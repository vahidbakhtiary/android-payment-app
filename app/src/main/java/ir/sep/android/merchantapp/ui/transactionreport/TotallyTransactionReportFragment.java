package ir.sep.android.merchantapp.ui.transactionreport;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import ir.sep.android.merchantapp.BuildConfig;
import ir.sep.android.merchantapp.Const;
import ir.sep.android.merchantapp.R;
import ir.sep.android.merchantapp.data.entities.Rahsepar;
import ir.sep.android.merchantapp.databinding.FragmentTotallyTransactionReportBinding;
import ir.sep.android.merchantapp.ui.support.SupportFragment;
import ir.sep.android.merchantapp.ui.transactionreport.master.TransactionReportMasterFragment;
import ir.sep.android.merchantapp.utils.CustomHorizontalBarChart;
import ir.sep.android.merchantapp.utils.DialogHelper;
import ir.sep.android.merchantapp.utils.JsonParserHelper;
import ir.sep.android.merchantapp.utils.PrecentageCalculator;
import ir.sep.android.merchantapp.utils.SharedPreferencesHelper;

@AndroidEntryPoint
public class TotallyTransactionReportFragment extends Fragment implements CustomHorizontalBarChart.BarChartCallBack {

    private TabCallback mCallback;

    public interface TabCallback {
        void setAnotherTab();
    }


    @Inject
    TransactionReportViewModel viewModelTransactionReport;
    @Inject
    DialogHelper dialogHelper;
    @Inject
    SharedPreferencesHelper sharedPreferencesHelper;
    @Inject
    JsonParserHelper jsonParserHelper;

    private String termId;
    private FragmentTotallyTransactionReportBinding binding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sendDefaultRequest();

    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTotallyTransactionReportBinding.inflate(inflater, container, false);
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupObserver();
    }

    private void sendDefaultRequest() {
        termId = sharedPreferencesHelper.select(Const.SHARED_PREF_TERMINAL_NUMBER_KEY);
        viewModelTransactionReport.setparamTotally(viewModelTransactionReport.makeTotallyRequestParam(termId));
    }

    private void setupObserver() {
        viewModelTransactionReport.getLiveDataTotallyReport().observe(getViewLifecycleOwner(), rahseparResponse -> {

            if (rahseparResponse == null) {
                dialogHelper.show(getView(), DialogHelper.DialogType.Error, getString(R.string.alert_faild_call_webservice));
                binding.viewEmpty.getRoot().setVisibility(View.VISIBLE);
                return;
            }

            if (!rahseparResponse.getSuccess()) {
                dialogHelper.show(getView(), DialogHelper.DialogType.Error, getString(R.string.alert_faild_call_webservice));
                binding.viewEmpty.getRoot().setVisibility(View.VISIBLE);
                return;
            }

            Log.e(Const.TAG, "onChanged: " + rahseparResponse.getData());
            List<Rahsepar.TotallyTransaction> totallyTransactionList = null;
            try {
                totallyTransactionList = getRahseparTotallyTransactionList(rahseparResponse.getData());
            } catch (Exception e) {
                e.printStackTrace();
                dialogHelper.show(getView(), DialogHelper.DialogType.Error, getString(R.string.alert_faild_message));
                binding.viewEmpty.getRoot().setVisibility(View.VISIBLE);
                return;
            }

            if (totallyTransactionList.size() == 0) {
                binding.viewEmpty.getRoot().setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
                return;
            }

            binding.mainContainer.setVisibility(View.VISIBLE);
            binding.viewEmpty.getRoot().setVisibility(View.GONE);

            binding.tvMerchant.setText(totallyTransactionList.get(0).getMerchantnumber());
            binding.tvTermid.setText(String.valueOf(totallyTransactionList.get(0).getTermid()));

            String startDate = viewModelTransactionReport.getStartDate().getValue().toString();
            String endDate = viewModelTransactionReport.getEndDate().getValue().toString();

            binding.tvTimeline.setText(startDate + " تا " + endDate);
            binding.tvReverseCount.setText(String.valueOf(totallyTransactionList.get(0).getReverseCount()));
            binding.tvFaildTransaction.setText(String.valueOf(totallyTransactionList.get(0).getFaildCount()));

            loadBardChart(totallyTransactionList.get(0));


        });


        viewModelTransactionReport.getTotallyReportLoadingState().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                binding.progressBar.setVisibility(View.VISIBLE);
                binding.viewEmpty.getRoot().setVisibility(View.GONE);
                binding.mainContainer.setVisibility(View.GONE);
            } else {
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }


    private void loadBardChart(Rahsepar.TotallyTransaction totallyTransaction) {
        double total = totallyTransaction.getBillCount() +
                totallyTransaction.getChargeCount() +
                totallyTransaction.getGroupchargeCount() +
                totallyTransaction.getTopupchargeCount() +
                totallyTransaction.getPurchaseCount();


        binding.barchar.removeAllViews();


        PrecentageCalculator pc = new PrecentageCalculator(total);
        binding.barchar.addView(genrateBarChart(Rahsepar.Prcodenum.Sale, getString(R.string.lbl_purchase), getResources().getColor(R.color.colorBlueGS), pc.calcPrecentage(totallyTransaction.getPurchaseCount()), totallyTransaction.getPurchaseSum(), totallyTransaction.getPurchaseCount()));
        binding.barchar.addView(genrateBarChart(Rahsepar.Prcodenum.Bill, getString(R.string.lbl_bill), getResources().getColor(R.color.colorInfluenzaGS), pc.calcPrecentage(totallyTransaction.getBillCount()), totallyTransaction.getBillSum(), totallyTransaction.getBillCount()));
        binding.barchar.addView(genrateBarChart(Rahsepar.Prcodenum.Charge, getString(R.string.lbl_charge), getResources().getColor(R.color.colorCalmGS), pc.calcPrecentage(totallyTransaction.getChargeCount()), totallyTransaction.getChargeSum(), totallyTransaction.getChargeCount()));
        binding.barchar.addView(genrateBarChart(Rahsepar.Prcodenum.Charge, getString(R.string.lbl_groupcharge), getResources().getColor(R.color.colorCalmGE), pc.calcPrecentage(totallyTransaction.getGroupchargeCount()), totallyTransaction.getGroupchargeSum(), totallyTransaction.getGroupchargeCount()));
        binding.barchar.addView(genrateBarChart(Rahsepar.Prcodenum.Charge, getString(R.string.lbl_topupcharge), getResources().getColor(R.color.colorRedGS), pc.calcPrecentage(totallyTransaction.getTopupchargeCount()), totallyTransaction.getTopupchargeSum(), totallyTransaction.getTopupchargeCount()));


//        if (BuildConfig.DEBUG) {
//            PrecentageCalculator pc = new PrecentageCalculator(90);
//            binding.barchar.addView(genrateBarChart(Rahsepar.Prcodenum.Sale, getString(R.string.lbl_purchase), getResources().getColor(R.color.colorBlueGS), pc.calcPrecentage(45), 105500, 45));
//            binding.barchar.addView(genrateBarChart(Rahsepar.Prcodenum.Bill, getString(R.string.lbl_bill), getResources().getColor(R.color.colorInfluenzaGS), pc.calcPrecentage(30), 800, 33));
//            binding.barchar.addView(genrateBarChart(Rahsepar.Prcodenum.Charge, getString(R.string.lbl_charge), getResources().getColor(R.color.colorCalmGS), pc.calcPrecentage(1), 100, 1));
//            binding.barchar.addView(genrateBarChart(Rahsepar.Prcodenum.Charge, getString(R.string.lbl_groupcharge), getResources().getColor(R.color.colorCalmGE), pc.calcPrecentage(6), 45000, 6));
//            binding.barchar.addView(genrateBarChart(Rahsepar.Prcodenum.Charge, getString(R.string.lbl_topupcharge), getResources().getColor(R.color.colorRedGS), pc.calcPrecentage(5), 500, 5));
//        } else {
//            PrecentageCalculator pc = new PrecentageCalculator(total);
//            binding.barchar.addView(genrateBarChart(Rahsepar.Prcodenum.Sale, getString(R.string.lbl_purchase), getResources().getColor(R.color.colorBlueGS), pc.calcPrecentage(totallyTransaction.getPurchaseCount()), totallyTransaction.getPurchaseSum(), totallyTransaction.getPurchaseCount()));
//            binding.barchar.addView(genrateBarChart(Rahsepar.Prcodenum.Bill, getString(R.string.lbl_bill), getResources().getColor(R.color.colorInfluenzaGS), pc.calcPrecentage(totallyTransaction.getBillCount()), totallyTransaction.getBillSum(), totallyTransaction.getBillCount()));
//            binding.barchar.addView(genrateBarChart(Rahsepar.Prcodenum.Charge, getString(R.string.lbl_charge), getResources().getColor(R.color.colorCalmGS), pc.calcPrecentage(totallyTransaction.getChargeCount()), totallyTransaction.getChargeSum(), totallyTransaction.getChargeCount()));
//            binding.barchar.addView(genrateBarChart(Rahsepar.Prcodenum.Charge, getString(R.string.lbl_groupcharge), getResources().getColor(R.color.colorCalmGE), pc.calcPrecentage(totallyTransaction.getGroupchargeCount()), totallyTransaction.getGroupchargeSum(), totallyTransaction.getGroupchargeCount()));
//            binding.barchar.addView(genrateBarChart(Rahsepar.Prcodenum.Charge, getString(R.string.lbl_topupcharge), getResources().getColor(R.color.colorRedGS), pc.calcPrecentage(totallyTransaction.getTopupchargeCount()), totallyTransaction.getTopupchargeSum(), totallyTransaction.getTopupchargeCount()));
//        }


    }

    private CustomHorizontalBarChart genrateBarChart(Rahsepar.Prcodenum prcodenum, String title, int color, float precentage, long amount, int count) {

        CustomHorizontalBarChart.BarInfo barInfo = new CustomHorizontalBarChart.BarInfo(prcodenum, title, precentage, amount, count, color);
        CustomHorizontalBarChart barChart = new CustomHorizontalBarChart(getActivity(), this, barInfo);

        return barChart;
    }

    @Override
    public void onclick(Rahsepar.Prcodenum prcodenum) {

        viewModelTransactionReport.setPrcodenum(prcodenum.getDescription());
        viewModelTransactionReport.setparam(viewModelTransactionReport.makeRequestParam(termId));
        Fragment navHostFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.navHostFragment);
        Fragment frag = navHostFragment.getChildFragmentManager().getFragments().get(0);

        if (frag instanceof TransactionReportMasterFragment) {
            ((TransactionReportMasterFragment) frag).setAnotherTab();
        }
    }


    List<Rahsepar.TotallyTransaction> getRahseparTotallyTransactionList(String json) throws Exception {
        return jsonParserHelper.convertJsonToObject(Rahsepar.TotallyTransaction.class, json);
    }

}
