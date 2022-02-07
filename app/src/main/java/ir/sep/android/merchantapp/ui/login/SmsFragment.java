package ir.sep.android.merchantapp.ui.login;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import ir.sep.android.merchantapp.Const;
import ir.sep.android.merchantapp.R;
import ir.sep.android.merchantapp.databinding.FragmentSmsBinding;
import ir.sep.android.merchantapp.ui.base.BaseFragment;
import ir.sep.android.merchantapp.utils.CustomSnackbar;
import ir.sep.android.merchantapp.utils.SharedPreferencesHelper;

import static android.os.SystemClock.uptimeMillis;
import static ir.sep.android.merchantapp.utils.KeyboardHelper.hideKeyboardAndClean;

@AndroidEntryPoint
public class SmsFragment extends BaseFragment {


    @Inject
    SharedPreferencesHelper sharedPreferencesHelper;

    private FragmentSmsBinding binding;
    private NavController navController;
    private int time = 0;
    private LoginViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSmsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        binding.btnBack.setOnClickListener(v -> requireActivity().onBackPressed());

        binding.btnTryAgain.setOnClickListener(v -> {
            binding.btnTryAgain.setVisibility(View.GONE);
            binding.tvTimer.setVisibility(View.VISIBLE);
            setTimer();
        });


        binding.etPin.setOtpCompletionListener(otp -> login(view));

        binding.btnLogin.setOnClickListener(v -> login(view));

        setTimer();

        //for focus on otpView
        new Handler().postDelayed(() -> {
            binding.etPin.dispatchTouchEvent(MotionEvent.obtain(uptimeMillis(), uptimeMillis(), MotionEvent.ACTION_DOWN, 0f, 0f, 0));
            binding.etPin.dispatchTouchEvent(MotionEvent.obtain(uptimeMillis(), uptimeMillis(), MotionEvent.ACTION_UP, 0f, 0f, 0));
        }, 100);
    }

    private void login(View view) {
        binding.btnLogin.setEnabled(false);

        String pin = binding.etPin.getText().toString();
        String appKey = sharedPreferencesHelper.select(Const.SHARED_PREF_APP_GUID_KEY);
        long terminal = Long.parseLong(sharedPreferencesHelper.select(Const.SHARED_PREF_TERMINAL_NUMBER_KEY));
        String mobileNO = sharedPreferencesHelper.select(Const.SHARED_PREF_MOBILE_NUMBER_KEY);
        String regKey = sharedPreferencesHelper.select(Const.SHARED_PREF_REGKEY_KEY);


        if (appKey.equals("") || regKey.equals("") || mobileNO.equals("") || terminal == 0) {
            new CustomSnackbar(view,
                    getString(R.string.alert_error),
                    CustomSnackbar.SnackbarType.Error
            );
            binding.btnLogin.setEnabled(true);
            return;
        }
        if (pin.length() != 5) {
            new CustomSnackbar(view,
                    getString(R.string.alert_pin_can_not_empty),
                    CustomSnackbar.SnackbarType.Error
            );
            binding.btnLogin.setEnabled(true);
            return;
        }


        binding.progressBar.setVisibility(View.VISIBLE);
        hideKeyboardAndClean(binding.etPin);

        viewModel.saveAuthenticationParams(terminal, mobileNO, appKey, pin, regKey)
                .observe(getViewLifecycleOwner(), merchantServiceEntity -> {
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    if (merchantServiceEntity == null) {
                        new CustomSnackbar(view,
                                getString(R.string.alert_faild_call_webservice),
                                CustomSnackbar.SnackbarType.Error
                        );
                        binding.btnLogin.setEnabled(true);
                        return;
                    }

                    if (merchantServiceEntity.getSuccess()) {
                        Long customerNo = merchantServiceEntity.getData().getCustomerNO();
                        String merchantKey = merchantServiceEntity.getData().getMerchantKey();
                        long merchantAppId = merchantServiceEntity.getData().getMerchantAppID();

                        sharedPreferencesHelper.insert(Const.SHARED_PREF_Merchant_GUID_KEY, merchantKey);
                        sharedPreferencesHelper.insert(Const.SHARED_PREF_Merchant_APPID_KEY, String.valueOf(merchantAppId));
                        sharedPreferencesHelper.insert(Const.SHARED_PREF_CUSTOMER_NO_KEY, String.valueOf(customerNo));
                        sharedPreferencesHelper.insert(Const.SHARED_PREF_IS_AUTHORIZED_SMS_KEY, "true");
                        navController.navigate(R.id.authorizedFragment);
                    } else {
                        new CustomSnackbar(view,
                                merchantServiceEntity.getMessage(),
                                CustomSnackbar.SnackbarType.Error
                        );
                        binding.btnLogin.setEnabled(true);
                    }

                });

    }

    private void setTimer() {
        time = Const.SMS_TRY_AGAIN / 1000;
        new CountDownTimer(Const.SMS_TRY_AGAIN, 1000) {

            public void onTick(long millis) {
                int seconds = (int) (millis / 1000) % 60;
                int minutes = (int) ((millis / (1000 * 60)) % 60);
                String text = String.format("%02d:%02d", minutes, seconds);


                binding.tvTimer.setText(text);
                time--;
            }

            public void onFinish() {
                binding.tvTimer.setVisibility(View.GONE);
                binding.btnTryAgain.setVisibility(View.VISIBLE);
            }

        }.start();
    }

    public String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }


}