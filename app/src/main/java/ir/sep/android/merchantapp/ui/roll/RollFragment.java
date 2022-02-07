package ir.sep.android.merchantapp.ui.roll;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import ir.sep.android.merchantapp.Const;
import ir.sep.android.merchantapp.R;
import ir.sep.android.merchantapp.data.entities.MerchantServiceBaseEntity;
import ir.sep.android.merchantapp.data.entities.MerchantServiceRollEntity;
import ir.sep.android.merchantapp.databinding.FragmentRollBinding;
import ir.sep.android.merchantapp.ui.base.BaseFragment;
import ir.sep.android.merchantapp.utils.CustomSnackbar;
import ir.sep.android.merchantapp.utils.KeyboardHelper;
import ir.sep.android.merchantapp.utils.SharedPreferencesHelper;

@AndroidEntryPoint
public class RollFragment extends BaseFragment {


    @Inject
    SharedPreferencesHelper sharedPreferencesHelper;

    private FragmentRollBinding binding;
    private NavController navController;
    private RollViewModel viewModel;
    private RequestHistoryAdapter historyAdapter;
    private RequestPaperAdapter requestPaperAdapter;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRollBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(RollViewModel.class);
        navController = Navigation.findNavController(view);

        setupRecyclerView();
        setupObserver(view);

        binding.btnSend.setOnClickListener(v -> {
            if (!isValidateEditText(binding.etTerminalNumber)) {
                binding.tilTerminalNumber.setError(getString(R.string.aler_terminal_number_mandatory));
                return;
            }

            binding.tvNote.setText(Html.fromHtml(getString(R.string.lbl_roll_note)));
            long terminalNo = Long.parseLong(binding.etTerminalNumber.getText().toString());
            String merchantKey = sharedPreferencesHelper.select(Const.SHARED_PREF_Merchant_GUID_KEY);
            String appKey = sharedPreferencesHelper.select(Const.SHARED_PREF_APP_GUID_KEY);

            //loading state
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.tilTerminalNumber.setError(null);
            //no need to keyboard and editText value
            KeyboardHelper.hideKeyboardAndClean(binding.etTerminalNumber);

            viewModel.sendRollRequest(merchantKey, appKey, terminalNo).observe(getViewLifecycleOwner(), merchantServiceEntity -> {
                //result is ready so hide progressBar
                binding.progressBar.setVisibility(View.INVISIBLE);
                //check result type
                if (merchantServiceEntity == null) {
                    new CustomSnackbar(view,
                            getString(R.string.alert_faild_call_webservice),
                            CustomSnackbar.SnackbarType.Error
                    );
                } else if ( merchantServiceEntity.getSuccess()!=null && merchantServiceEntity.getSuccess()) {
                    new CustomSnackbar(view,
                            "درخواست شما ثبت شد\n" +
                                    "شماره تیکیت: " + merchantServiceEntity.getData().getTermPMID(),
                            CustomSnackbar.SnackbarType.Info,
                            "باشه"
                    );
                } else {
                    new CustomSnackbar(view,
                            merchantServiceEntity.getMessage()!=null ? merchantServiceEntity.getMessage().split("\\R")[0]
                                    : "سرویس مورد نظر پاسخگو نمی باشد",
                            CustomSnackbar.SnackbarType.Error
                    );
                }
            });
        });

    }

    private void setupRecyclerView() {
        historyAdapter = new RequestHistoryAdapter();
        binding.rvHistory.setHasFixedSize(true);
        binding.rvHistory.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        binding.rvHistory.setAdapter(historyAdapter);

        requestPaperAdapter = new RequestPaperAdapter();
        binding.rvOpenReq.setHasFixedSize(true);
        binding.rvOpenReq.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        binding.rvOpenReq.setAdapter(requestPaperAdapter);
    }


    private void setupObserver(View view) {
        String merchantKey = sharedPreferencesHelper.select(Const.SHARED_PREF_Merchant_GUID_KEY);
        String appKey = sharedPreferencesHelper.select(Const.SHARED_PREF_APP_GUID_KEY);

        viewModel.getRollHistory(merchantKey, appKey).observe(getViewLifecycleOwner(), new Observer<MerchantServiceRollEntity>() {
            @Override
            public void onChanged(MerchantServiceRollEntity merchantServiceEntity) {

                if (merchantServiceEntity == null) {
                    new CustomSnackbar(view,
                            getString(R.string.alert_faild_call_webservice),
                            CustomSnackbar.SnackbarType.Error
                    );
                    return;
                }

                if (merchantServiceEntity.getSuccess()) {
                    MerchantServiceBaseEntity.Data data = merchantServiceEntity.getData();

                    if (data.getReceiptPaperList() != null) {
                        if (data.getReceiptPaperList().size() > 0) {
                            binding.tvHistory.setVisibility(View.VISIBLE);
                            binding.rvHistory.setVisibility(View.VISIBLE);
                            historyAdapter.submitList(data.getReceiptPaperList());
                        }
                    }

                    if (data.getRequestPaperList() != null) {
                        if (data.getRequestPaperList().size() > 0) {
                            binding.tvHistory.setVisibility(View.VISIBLE);
                            binding.rvOpenReq.setVisibility(View.VISIBLE);
                            requestPaperAdapter.submitList(data.getRequestPaperList());
                        }
                    }


                    if (data.getRequestPaperList() == null && data.getReceiptPaperList() == null) {
                        binding.tvHistory.setVisibility(View.GONE);
                        binding.rvHistory.setVisibility(View.GONE);
                    }


                } else {
                    new CustomSnackbar(view,
                            merchantServiceEntity.getMessage(),
                            CustomSnackbar.SnackbarType.Error
                    );
                }

            }
        });
    }
}