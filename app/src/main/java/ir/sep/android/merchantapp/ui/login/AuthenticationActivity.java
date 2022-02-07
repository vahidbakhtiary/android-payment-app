package ir.sep.android.merchantapp.ui.login;

import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.firebase.messaging.FirebaseMessaging;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import ir.sep.android.merchantapp.BuildConfig;
import ir.sep.android.merchantapp.Const;
import ir.sep.android.merchantapp.R;
import ir.sep.android.merchantapp.databinding.ActivityAuthenticationBinding;
import ir.sep.android.merchantapp.ui.base.BaseActivity;
import ir.sep.android.merchantapp.utils.SharedPreferencesHelper;

@AndroidEntryPoint
public class AuthenticationActivity extends BaseActivity {

    @Inject
    SharedPreferencesHelper sharedPreferencesHelper;
    private ActivityAuthenticationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthenticationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseMessaging.getInstance().subscribeToTopic("All");

        //if user is authorized skip login
        if (BuildConfig.DEBUG) {

//            sharedPreferencesHelper.insert("MobileNO", "09302020025");
//            sharedPreferencesHelper.insert("MerchantAppId", "1000589335");
//            sharedPreferencesHelper.insert("TerminalNO", "2019");
//            sharedPreferencesHelper.insert("AppKeyGUID", "5abb4144-58af-4923-922a-fdac7c8df4e3");
//            sharedPreferencesHelper.insert("RegKey", "0RvvwnSe+kDK5ddE7SxNNg==");
//            sharedPreferencesHelper.insert("isAuthrized", "true");
//            sharedPreferencesHelper.insert("CustomerNO", "6");
//            sharedPreferencesHelper.insert("MerchantKeyGUID", "2ee31dc3-d960-4839-b59e-c92b3573b19e");
        }
        //check from sharedPreference
        String isAuthorizeSMS = sharedPreferencesHelper.select(Const.SHARED_PREF_IS_AUTHORIZED_SMS_KEY);
        String isAuthorizePass = sharedPreferencesHelper.select(Const.SHARED_PREF_IS_AUTHORIZED_PASS_KEY);
        String isAuthorizeSession = sharedPreferencesHelper.select(Const.SHARED_PREF_IS_AUTHORIZED_SESSION_KEY);

        if (!isAuthorizeSMS.isEmpty() && !isAuthorizePass.isEmpty() && Boolean.parseBoolean(isAuthorizeSession)) {
            //find nav navController
            NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            NavController navController = navHostFragment.getNavController();
            //navigate to mainActivity
            navController.navigate(R.id.mainActivity);
            finish();
        }else if(isAuthorizeSMS.isEmpty())
        {
            //find nav navController
            NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            NavController navController = navHostFragment.getNavController();
            //navigate to mainActivity
            navController.navigate(R.id.loginFragment);
        }
        else if(isAuthorizePass.isEmpty() || !Boolean.parseBoolean(isAuthorizeSession))
        {
            //find nav navController
            NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            NavController navController = navHostFragment.getNavController();
            //navigate to mainActivity
            navController.navigate(R.id.authorizedFragment);
            //finish();
        }

        //just for debugging purpose
//        if (BuildConfig.DEBUG) {
//            NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
//            NavController navController = navHostFragment.getNavController();
//            navController.navigate(R.id.action_loginFragment_to_main_activity);
//            finish();
//        }

    }

}