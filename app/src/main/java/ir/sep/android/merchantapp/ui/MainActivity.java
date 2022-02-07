package ir.sep.android.merchantapp.ui;


import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.gms.dynamic.SupportFragmentWrapper;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.Set;

import ir.sep.android.merchantapp.R;
import ir.sep.android.merchantapp.databinding.ActivityMainBinding;
import ir.sep.android.merchantapp.ui.base.BaseActivity;
import ir.sep.android.merchantapp.ui.support.SupportFragment;

import static androidx.navigation.ui.NavigationUI.setupActionBarWithNavController;
import static androidx.navigation.ui.NavigationUI.setupWithNavController;


public class MainActivity extends BaseActivity {


    private ActivityMainBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        //find NavController
        navController = Navigation.findNavController(this, R.id.navHostFragment);
        //set bottomNavigation
        setupWithNavController(binding.bottomNavigation, navController);
        //set toolbar
        setSupportActionBar(binding.toolbar);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                //fragments that do not need back button in toolbar
                Set.of(R.id.dashboardFragment,
                        R.id.transactionReportMasterFragment,
                        R.id.supportFragment,
                        R.id.settingFragment)
        ).build();
        setupActionBarWithNavController(this, navController, appBarConfiguration);

        //hide bottomNavigation on some fragments
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.dashboardFragment ||
                    destination.getId() == R.id.transactionReportMasterFragment ||
                    destination.getId() == R.id.supportFragment ||
                    destination.getId() == R.id.settingFragment) {
                binding.bottomNavigation.setVisibility(View.VISIBLE);
            } else {
                binding.bottomNavigation.setVisibility(View.GONE);
            }
        });
    }


//    @Override
////    public boolean onKeyDown(int keyCode, KeyEvent event) {
////
////        if (keyCode == KeyEvent.KEYCODE_BACK) {
////            Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.navHostFragment);
////            Fragment frag = navHostFragment.getChildFragmentManager().getFragments().get(0);
////
////            if(frag instanceof SupportFragment)
////            {
////               ((SupportFragment) frag).myOnKeyDown(keyCode);
////            }
////        }
////        return super.onKeyDown(keyCode, event);
////
////    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}