package ir.sep.android.merchantapp.ui.supervisor;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import ir.sep.android.merchantapp.Const;
import ir.sep.android.merchantapp.R;
import ir.sep.android.merchantapp.databinding.FragmentSupervisorBinding;
import ir.sep.android.merchantapp.ui.base.BaseFragment;
import ir.sep.android.merchantapp.utils.CustomSnackbar;
import ir.sep.android.merchantapp.utils.DialogHelper;
import ir.sep.android.merchantapp.utils.KeyboardHelper;
import ir.sep.android.merchantapp.utils.SharedPreferencesHelper;

@AndroidEntryPoint
public class SupervisorFragment extends BaseFragment {

    @Inject
    DialogHelper dialogHelper;
    @Inject
    SharedPreferencesHelper sharedPreferencesHelper;

    private SupervisorViewModel viewModel;

    private FragmentSupervisorBinding binding;
    private NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSupervisorBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(SupervisorViewModel.class);

        binding.btnReceiveCode.setOnClickListener(v -> {
            if (!isValidateEditText(binding.etTerminalNumber)) {
                binding.tilTerminalNumber.setError("شماره ترمینال اجباری می باشد");
                return;
            }

            long terminalNo = Long.parseLong(binding.etTerminalNumber.getText().toString());
            String merchantKey = sharedPreferencesHelper.select(Const.SHARED_PREF_Merchant_GUID_KEY);
            String appKey = sharedPreferencesHelper.select(Const.SHARED_PREF_APP_GUID_KEY);

            //loading state
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.tilTerminalNumber.setError(null);
            //no need to keyboard and editText value
            KeyboardHelper.hideKeyboardAndClean(binding.etTerminalNumber);

            viewModel.posResetPW(merchantKey, appKey, terminalNo).observe(getViewLifecycleOwner(), merchantServiceEntity -> {
                //result is ready so hide progressBar
                binding.progressBar.setVisibility(View.INVISIBLE);
                //check result type
                if (merchantServiceEntity == null) {
                    new CustomSnackbar(view,
                            getString(R.string.alert_faild_call_webservice),
                            CustomSnackbar.SnackbarType.Error
                    );
                } else if (merchantServiceEntity.getSuccess()) {
                    new CustomSnackbar(view,
                            "رمز جدید: " + merchantServiceEntity.getData().getPw(),
                            CustomSnackbar.SnackbarType.Info,
                            "باشه"
                    );
                } else {
                    new CustomSnackbar(view,
                            merchantServiceEntity.getMessage().split("\\R")[0],
                            CustomSnackbar.SnackbarType.Error
                    );
                }
            });
        });

    }
}