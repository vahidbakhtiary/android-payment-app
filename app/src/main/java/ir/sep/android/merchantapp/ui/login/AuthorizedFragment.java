package ir.sep.android.merchantapp.ui.login;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import ir.sep.android.merchantapp.Const;
import ir.sep.android.merchantapp.R;
import ir.sep.android.merchantapp.databinding.FragmentAuthorizedBinding;
import ir.sep.android.merchantapp.ui.base.BaseFragment;
import ir.sep.android.merchantapp.utils.CustomSnackbar;
import ir.sep.android.merchantapp.utils.SharedPreferencesHelper;

import static android.content.Context.MODE_PRIVATE;
import static android.os.SystemClock.uptimeMillis;
import static ir.sep.android.merchantapp.utils.KeyboardHelper.hideKeyboardAndClean;

@AndroidEntryPoint
public class AuthorizedFragment extends BaseFragment {


    @Inject
    SharedPreferencesHelper sharedPreferencesHelper;
    @Inject
    LoginViewModel viewModel;

    private FragmentAuthorizedBinding binding;
    private NavController navController;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //sharedPreferencesHelper=new SharedPreferencesHelper(getContext());
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAuthorizedBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String oldPass = sharedPreferencesHelper.select(Const.SHARED_PREF_IS_AUTHORIZED_PASS_KEY);
        if (!oldPass.isEmpty()) {
            binding.tvHelp.setText(getString(R.string.lbl_enter_password));
        }
        navController = Navigation.findNavController(view);


        binding.etPin.setOtpCompletionListener(otp -> login(view));

       // binding.btnLogin.setOnClickListener(v -> login(view));


        //for focus on otpView
        new Handler().postDelayed(() -> {
            binding.etPin.dispatchTouchEvent(MotionEvent.obtain(uptimeMillis(), uptimeMillis(), MotionEvent.ACTION_DOWN, 0f, 0f, 0));
            binding.etPin.dispatchTouchEvent(MotionEvent.obtain(uptimeMillis(), uptimeMillis(), MotionEvent.ACTION_UP, 0f, 0f, 0));
        }, 100);
    }

    private void login(View view) {
        //binding.btnLogin.setEnabled(false);

        String pin = binding.etPin.getText().toString();


        if (pin.length() != 4) {
            new CustomSnackbar(view,
                    getString(R.string.alert_pass_can_not_empty),
                    CustomSnackbar.SnackbarType.Error
            );
           // binding.btnLogin.setEnabled(true);
            return;
        }


        binding.progressBar.setVisibility(View.VISIBLE);
        hideKeyboardAndClean(binding.etPin);

        viewModel.getPass().observe(getViewLifecycleOwner(), pass -> {
           // binding.btnLogin.setEnabled(true);
            binding.progressBar.setVisibility(View.GONE);


            String oldPass = sharedPreferencesHelper.select(Const.SHARED_PREF_IS_AUTHORIZED_PASS_KEY);
            if (oldPass.isEmpty()) {
                sharedPreferencesHelper.insert(Const.SHARED_PREF_IS_AUTHORIZED_PASS_KEY, pin);
                sharedPreferencesHelper.insert(Const.SHARED_PREF_IS_AUTHORIZED_SESSION_KEY, "true");

                navController.navigate(R.id.action_authorizedFragment_to_mainActivity);
                return;

            }


            if (oldPass.equals(pin)) {
                navController.navigate(R.id.action_authorizedFragment_to_mainActivity);
            } else {
                new CustomSnackbar(view,
                        getString(R.string.alert_invalid_pass_message),
                        CustomSnackbar.SnackbarType.Error
                );

            }


        });
        viewModel.setPass(pin);


    }


    public String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }


}