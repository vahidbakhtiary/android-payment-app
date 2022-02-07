package ir.sep.android.merchantapp.ui.dashboard;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.model.GradientColor;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import ir.sep.android.merchantapp.Const;
import ir.sep.android.merchantapp.R;
import ir.sep.android.merchantapp.data.entities.Rahsepar;
import ir.sep.android.merchantapp.data.entities.ShamsiDate;
import ir.sep.android.merchantapp.databinding.FragmentDashboardBinding;
import ir.sep.android.merchantapp.ui.base.BaseFragment;
import ir.sep.android.merchantapp.ui.dashboard.menu.Menu;
import ir.sep.android.merchantapp.ui.dashboard.menu.MenuAdapter;
import ir.sep.android.merchantapp.ui.dashboard.menu.MenuListener;
import ir.sep.android.merchantapp.ui.inbox.InboxViewModel;
import ir.sep.android.merchantapp.ui.transactionreport.TransactionReportViewModel;
import ir.sep.android.merchantapp.utils.DialogHelper;
import ir.sep.android.merchantapp.utils.JsonParserHelper;
import ir.sep.android.merchantapp.utils.SharedPreferencesHelper;

import static android.app.Activity.RESULT_OK;


@AndroidEntryPoint
public class DashboardFragment extends BaseFragment implements MenuListener {

    public static final int REQUEST_SPEECH = 0;

    @Inject
    DashboardViewModel dashboardViewModel;
    @Inject
    InboxViewModel inboxViewModel;
    @Inject
    TransactionReportViewModel viewModelTransactionReport;


    @Inject
    DialogHelper dialogHelper;

    @Inject
    SharedPreferencesHelper sharedPreferencesHelper;

    @Inject
    JsonParserHelper jsonParserHelper;

    private NavController navController;
    private FragmentDashboardBinding binding;
    private MenuAdapter menuAdapter;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(getActivity(), new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String token = instanceIdResult.getToken();
                Log.e(Const.TAG, token);
            }
        });

        setHasOptionsMenu(true);


        setBaseInfo();
        initMenu();
        setMonthFilter();
        dashboardViewModel.sendRequest(dashboardViewModel.makeTotallyRequestParameters(sharedPreferencesHelper.select(Const.SHARED_PREF_TERMINAL_NUMBER_KEY)));
        setupObserver();


    }

    @Override
    public void onCreateOptionsMenu(@NotNull android.view.Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.dashboard_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_voice_assistant:
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "fa");
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "برو به صفحه...");
                startActivityForResult(intent, REQUEST_SPEECH);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void setBaseInfo() {
        String merchantNumber = sharedPreferencesHelper.select(Const.SHARED_PREF_Merchant_APPID_KEY);
        binding.tvMerchantNumber.setText(merchantNumber);
    }

    private void initMenu() {
        menuAdapter = new MenuAdapter(this);
        binding.rvMenu.setHasFixedSize(true);
        binding.rvMenu.setLayoutManager(new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false));
        binding.rvMenu.setAdapter(menuAdapter);
    }

    private void setMonthFilter() {
        binding.tvDate.setText(dashboardViewModel.today.getMonthName());
        binding.tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MonthPickerDialog dialog = new MonthPickerDialog();
                Bundle bundle = new Bundle();
                bundle.putString("currecnt_month", binding.tvDate.getText().toString());
                dialog.setArguments(bundle);
                dialog.show(getChildFragmentManager(), "month_dialog");
            }
        });


        binding.tvMore.setOnClickListener(view1 -> {
            viewModelTransactionReport.setStartDate(dashboardViewModel.today.getYear(), dashboardViewModel.today.getMonth(), 1);
            viewModelTransactionReport.setEndDate(dashboardViewModel.today.getYear(), dashboardViewModel.today.getMonth(), dashboardViewModel.today.getMonthLength());
            viewModelTransactionReport.setPrcodenum("");
            viewModelTransactionReport.setPosconditioncode("");
            //viewModelTransactionReport.setShaparakstatus(Rahsepar.Shaparakstatus.Success.getDescription());
            viewModelTransactionReport.setShaparakstatus("");
            viewModelTransactionReport.setSettlestatus("");
            navController.navigate(R.id.action_dashboardFragment_to_transactionReportMasterFragment);
        });

    }

    private void setupObserver() {

        dashboardViewModel.getTotallyReport().observe(getViewLifecycleOwner(), rahseparResponse -> {
            if (rahseparResponse == null) {
                dialogHelper.show(getView(), DialogHelper.DialogType.Error, getString(R.string.alert_faild_call_webservice));
                return;
            }

            if (rahseparResponse.getSuccess()) {
                List<Rahsepar.TotallyTransaction> totallyTransactionList = null;
                try {
                    totallyTransactionList = getRahseparTotallyTransactionList(rahseparResponse.getData());
                } catch (Exception e) {
                    e.printStackTrace();
                    dialogHelper.show(getView(), DialogHelper.DialogType.Error, getString(R.string.alert_faild_message));
                    return;
                }
                String[] chartItems = {
                        getString(R.string.lbl_count_bill),
                        getString(R.string.lbl_count_charg),
                        getString(R.string.lbl_count_sale)};

                if (totallyTransactionList.size() == 0) {
                    DecimalFormat mFormat = new DecimalFormat("###,###,###,##0");
                    // fillChart(new String[]{"مجموع مبلغ شارژ", "مجموع مبلغ قبض"}, new float[]{0, 0});
                    fillChart(chartItems, new int[]{0, 0, 0});
//                    fillChart(chartItems,
//                            new int[]{
//                                    15,
//                                    10,
//                                    20
//                            });
                    binding.tvSum.setText(" 0 " + getString(R.string.lbl_rial));
                } else {
                    DecimalFormat mFormat = new DecimalFormat("###,###,###,##0");
                    binding.tvSum.setText(mFormat.format(totallyTransactionList.get(0).getPurchaseSum()) + getString(R.string.lbl_rial));
                    //fillChart(new String[]{getString(R.string.lbl_totaly_charg), getString(R.string.lbl_totaly_bill)}, new float[]{totallyTransactionList.get(0).getChargeSum(), totallyTransactionList.get(0).getBillSum()});
                    fillChart(chartItems,
                            new int[]{
                                    totallyTransactionList.get(0).getBillCount(),
                                    totallyTransactionList.get(0).getChargeCount(),
                                    totallyTransactionList.get(0).getPurchaseCount()
                            });


                }

            } else {
                dialogHelper.show(getView(), DialogHelper.DialogType.Error, getString(R.string.alert_faild_call_webservice));
                return;
            }

        });


        inboxViewModel.getUnReadMessage().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer count_Notification) {


                if (count_Notification == 0)
                    return;

                int childCount = binding.rvMenu.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    MaterialCardView cardView = (MaterialCardView) binding.rvMenu.getChildAt(i);
                    if (Integer.parseInt(cardView.getTag().toString()) == R.string.lbl_menu_massages) {
                        TextView tvNotification = cardView.findViewById(R.id.tv_Notification);
                        tvNotification.setVisibility(View.VISIBLE);
                        tvNotification.setText(String.valueOf(count_Notification));
                        break;
                    }
                }


            }
        });


        dashboardViewModel.getTodayShamsiDateLiveData().observe(getViewLifecycleOwner(), new Observer<ShamsiDate>() {
            @Override
            public void onChanged(ShamsiDate shamsiDate) {
                try {
                    int month = shamsiDate.getMonth() - 1;
                    List<String> list = Arrays.asList(getResources().getStringArray(R.array.month_array));
                    binding.tvDate.setText(list.get(month));

                } catch (Exception ex) {

                }
            }
        });
    }

    List<Rahsepar.TotallyTransaction> getRahseparTotallyTransactionList(String json) throws Exception {
        return jsonParserHelper.convertJsonToObject(Rahsepar.TotallyTransaction.class, json);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SPEECH) {
            if (resultCode == RESULT_OK) {

                if (data == null) {
                    return;
                }

                ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                if (matches != null && !matches.isEmpty()) {
                    String mostLikelyThingHeard = matches.get(0);
                    for (Menu item : menuAdapter.getCurrentList()) {
                        if (requireContext().getText(item.getTitle()).toString().trim().contains(mostLikelyThingHeard.toLowerCase().trim())) {
                            navController.navigate(item.getAction());
                            return;
                        }
                    }
                    PopupMenu p = new PopupMenu(getActivity(), null);
                    android.view.Menu menu1 = p.getMenu();
                    getActivity().getMenuInflater().inflate(R.menu.bottom_navigation_menu, menu1);
                    for (int i = 0; i < 4; i++) {
                        if (menu1.getItem(i).getTitle().toString().trim().contains(mostLikelyThingHeard.toLowerCase().trim())) {
                            navController.navigate(menu1.getItem(i).getItemId());
                        }
                    }
                } else {
                    /* didn't hear anything */
                }
            }
        }
    }

//    RahseparRequest makeRequestParam() {
//        RahseparRequest request = new RahseparRequest();
//        request.setCustomerNumber(null);
//        request.setMerchantNumber(null);
//        request.setTerminalNumber(null);
//        request.setExportTo(0);
//
//
//        String termId = sharedPreferencesHelper.select(Const.SHARED_PREF_TERMINAL_NUMBER_KEY);
//        ServiceColumn column1 = new ServiceColumn("تاریخ تراکنش", 60, 32, "1399/02/01", "txndate", "TxnDate", true, "");
//        ServiceColumn column2 = new ServiceColumn("تاریخ تراکنش", 60, 22, "1399/03/01", "txndate", "TxnDate", true, "");
//        ServiceColumn column3 = new ServiceColumn("شماره ترمینال", 50, 0, termId, "termid", "termid", true, "");
//
//
//        List<ServiceColumn> serviceColumnList = new ArrayList<>();
//        serviceColumnList.add(column1);
//        serviceColumnList.add(column2);
//        serviceColumnList.add(column3);
//
//
//        request.setServiceColumns(serviceColumnList);
//        request.setUserName("rahsepar");
//        request.setPassword("1nUSJwZZjCRsTg6GWnx94jDhtM0=");
//        request.setRepWebserviceKey("812ccce3-479a-45a0-a5d2-38543437d987");
//        request.setCustomerKey("30250e1e-e56a-4d4f-ab3c-34a7a20be160");
//        request.setRepRelationKey("2bf5c679-0946-44d5-a289-988aeb3df014");
//        request.setCanSplitFun(false);
//        request.setOfsetCount(-1);
//        request.setFetchCount(-1);
//
//
//        Gson gson = new Gson();
//        String json = gson.toJson(request);
//        return request;
//
//
//    }

    @Override
    public void onMenuClicked(Menu menu) {
        if (navController.getCurrentDestination() == null || navController.getCurrentDestination().getId() != R.id.dashboardFragment) {
            return;
        }
        if (menu.getAction() == 0) {
            Toast.makeText(getContext(), "درحال توسعه", Toast.LENGTH_SHORT).show();
            return;
        }
        navController.navigate(menu.getAction());
    }

    private void fillChart(String[] col, int[] val) {
        Typeface typeface = ResourcesCompat.getFont(requireActivity(), R.font.iran_sans_mobile_light);

        BarChart chart = binding.barchart;

        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < val.length; i++) {
            entries.add(new BarEntry(i + 1, val[i]));
        }


        BarDataSet dataset = new BarDataSet(entries, "");
        dataset.setColors(Color.GRAY, Color.GREEN, Color.GRAY);
        MyValueFormatter myValueFormatter = new MyValueFormatter(col);
        dataset.setValueFormatter(myValueFormatter);
        BarData data = new BarData(dataset);
        data.setBarWidth(0.4f);


        //start gradian
        int startColor1 = ContextCompat.getColor(getActivity(), R.color.colorelectricVioletGS);
        int endColor1 = ContextCompat.getColor(getActivity(), R.color.colorelectricVioletGE);

        int startColor2 = ContextCompat.getColor(getActivity(), R.color.tttt);
        int endColor2 = ContextCompat.getColor(getActivity(), R.color.dddd);

        int startColor3 = ContextCompat.getColor(getActivity(), R.color.red_pink_start);
        int endColor3 = ContextCompat.getColor(getActivity(), R.color.red_pink_stop);
        //end gradian

        List<GradientColor> gradientColors = new ArrayList<>();
        gradientColors.add(new GradientColor(startColor1, endColor1));
        gradientColors.add(new GradientColor(startColor2, endColor2));
        // gradientColors.add(new GradientColor(startColor3, endColor3));
        chart.setDescription(null);

        data.setValueTextSize(14);
        dataset.setGradientColors(gradientColors);
        data.setValueTypeface(typeface);
        chart.setData(data);

        chart.getXAxis().setTextSize(12);
        chart.animateY(500);
        chart.getAxisRight().setDrawAxisLine(false);
        chart.getAxisLeft().setDrawAxisLine(false);
        chart.getXAxis().setDrawAxisLine(false);
        chart.getXAxis().setDrawGridLines(false);
        chart.getAxisRight().setDrawLabels(false);
        chart.getAxisLeft().setDrawLabels(false);
        chart.getLegend().setEnabled(false);

        chart.getXAxis().setTypeface(typeface);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getXAxis().setGranularity(1f); // minimum axis-step (interval) is 1
        chart.getXAxis().setValueFormatter(myValueFormatter);

        chart.setViewPortOffsets(0f, 0f, 0f, 55f);

        //   chart.setVisibleYRangeMaximum(300000, YAxis.AxisDependency.LEFT);


//        YAxis y = chart.getAxisLeft();
//        y.setAxisMaxValue(100);
//        y.setAxisMinValue(0);
//        y.setLabelCount(6);

//        float minValue = data.getYMin();
//        float maxValue = data.getYMax();
//        //Initial values for Y axis
//        int extraSpace = (int) (maxValue / val.length);
//        int yMax = (int) maxValue + (extraSpace < 1 ? 1 : extraSpace);
//        int yMin = (int) minValue - 1;

        //  xAxis.setValueFormatter(new MyAxisValueFormatter(mXAxisNames));
        YAxis yAxis = chart.getAxisLeft();
        yAxis.setLabelCount(val.length, true);
        yAxis.setTextColor(Color.BLACK);
        yAxis.setTextSize(7f);
        yAxis.setDrawGridLines(false);
        yAxis.setDrawLabels(true);
        yAxis.setGranularity(1f);
        yAxis.setGranularityEnabled(true);
        yAxis.setSpaceTop(10f);
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yAxis.setAxisMinimum(-1f);
        yAxis.setDrawZeroLine(true);
        yAxis.setAxisMaximum(dashboardViewModel.generateMaxForChar(val));
    }


    public class MyValueFormatter extends ValueFormatter {

        final ArrayList<String> xAxisLabel = new ArrayList<>();
        private DecimalFormat mFormat;

        public MyValueFormatter(String... params) {
            xAxisLabel.addAll(Arrays.asList(params));
            mFormat = new DecimalFormat("###,###,###,##0");
        }

        @Override
        public String getFormattedValue(float value) {
            return mFormat.format(value) + "عدد";
        }

        @Override
        public String getAxisLabel(float value, AxisBase axis) {
            return xAxisLabel.get(((int) value) - 1);
        }

    }

}