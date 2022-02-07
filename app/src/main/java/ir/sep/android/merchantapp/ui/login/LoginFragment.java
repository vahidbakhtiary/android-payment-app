package ir.sep.android.merchantapp.ui.login;

import android.content.Intent;
import android.net.Uri;
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
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import ir.sep.android.merchantapp.Const;
import ir.sep.android.merchantapp.R;
import ir.sep.android.merchantapp.databinding.FragmentLoginBinding;
import ir.sep.android.merchantapp.ui.base.BaseFragment;
import ir.sep.android.merchantapp.utils.CustomSnackbar;
import ir.sep.android.merchantapp.utils.GUIDHelper;
import ir.sep.android.merchantapp.utils.SharedPreferencesHelper;

@AndroidEntryPoint
public class LoginFragment extends BaseFragment {


    @Inject
    GUIDHelper guidHelper;
    @Inject
    SharedPreferencesHelper sharedPreferencesHelper;


    private LoginViewModel viewModel;
    private NavController navController;
    private FragmentLoginBinding binding;
    private String token = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(getActivity(), new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                token = instanceIdResult.getToken();
            }
        });
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        navController = Navigation.findNavController(view);

        binding.etTerminalNumber.addTextChangedListener( textWatcherTerminal);
        binding.etMobileNo.addTextChangedListener(textWatcherMobileNo);


        binding.btnCallSupport.setOnClickListener(v ->
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(String.format("tel:%s", Const.CALL_SUPPORTER_PHONE)))));

        binding.btnReceiveCode.setOnClickListener(v -> {

            if (!isValidateEditText(binding.etTerminalNumber)) {
                binding.tilTerminalNumber.setError(getString(R.string.aler_terminal_number_mandatory));
                return;
            } else if (!isValidateEditText(binding.etMobileNo)) {
                binding.tilPhoneNumber.setError(getString(R.string.alert_mobile_number_not_valid));
                return;
            }


            binding.btnReceiveCode.setEnabled(false);

            if (!isValidateEditText(binding.etMobileNo, binding.etTerminalNumber)) {
                new CustomSnackbar(view,
                        getString(R.string.alert_edittext_can_not_be_empty),
                        CustomSnackbar.SnackbarType.Warning
                );
                binding.btnReceiveCode.setEnabled(true);
                return;
            }

            long terminalNo = Long.parseLong(binding.etTerminalNumber.getText().toString());
            String mobileNo = binding.etMobileNo.getText().toString();

            if (mobileNo.length() != 11 || !mobileNo.startsWith("09")) {
                new CustomSnackbar(view,
                        getString(R.string.alert_mobile_number_not_valid),
                        CustomSnackbar.SnackbarType.Warning
                );
                binding.btnReceiveCode.setEnabled(true);
                return;
            }


            String guid = guidHelper.getGUID();
            // String guid = token;
            sharedPreferencesHelper.insert(Const.SHARED_PREF_APP_GUID_KEY, guid);
            sharedPreferencesHelper.insert(Const.SHARED_PREF_TERMINAL_NUMBER_KEY, String.valueOf(terminalNo));
            sharedPreferencesHelper.insert(Const.SHARED_PREF_MOBILE_NUMBER_KEY, mobileNo);


            binding.progressBar.setVisibility(View.VISIBLE);
            viewModel.getRegistrationLiveData(terminalNo, mobileNo, guid).observe(getViewLifecycleOwner(), merchantServiceEntity -> {
                binding.progressBar.setVisibility(View.INVISIBLE);
                if (merchantServiceEntity == null) {
                    new CustomSnackbar(view,
                            getString(R.string.alert_faild_call_webservice),
                            CustomSnackbar.SnackbarType.Error
                    );
                    binding.btnReceiveCode.setEnabled(true);
                    return;
                }

                if (merchantServiceEntity.getSuccess()) {
                    sharedPreferencesHelper.insert(Const.SHARED_PREF_REGKEY_KEY, merchantServiceEntity.getData().getRegKey());
                    navController.navigate(R.id.action_loginFragment_to_smsFragment);
                } else {
                    new CustomSnackbar(view,
                            merchantServiceEntity.getMessage(),
                            CustomSnackbar.SnackbarType.Error
                    );
                    binding.btnReceiveCode.setEnabled(true);
                }

            });

        });
    }

    private TextWatcher textWatcherMobileNo = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            binding.tilPhoneNumber.setError(null);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private TextWatcher textWatcherTerminal = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            binding.tilTerminalNumber.setError(null);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
