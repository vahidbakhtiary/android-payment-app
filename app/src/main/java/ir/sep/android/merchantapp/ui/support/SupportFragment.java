package ir.sep.android.merchantapp.ui.support;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import ir.sep.android.merchantapp.Const;
import ir.sep.android.merchantapp.R;
import ir.sep.android.merchantapp.databinding.FragmentRollBinding;
import ir.sep.android.merchantapp.databinding.FragmentSupportBinding;
import ir.sep.android.merchantapp.ui.base.BaseFragment;
import ir.sep.android.merchantapp.ui.roll.RollViewModel;


public class SupportFragment extends BaseFragment {


    private NavController navController;
    private FragmentSupportBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSupportBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        //String url="http://10.0.2.2:8020/Home/PosError";



        binding.webView.setWebViewClient(new AppWebViewClients(Const.SUPPORT_BASEUR, binding.webView,binding.viewEmpty.getRoot(),binding.progressBar));
        binding.webView.loadUrl(Const.SUPPORT_BASEUR);
        binding.webView.getSettings().setJavaScriptEnabled(true);

    }

    public void myOnKeyDown(int keyCode){
        //do whatever you want here
//        switch (keyCode) {
//            case KeyEvent.KEYCODE_BACK:
//                if (binding.webview.canGoBack()) {
//                    binding.webview.goBack();
//                } else {
//                    //finish();
//                }
//
//        }
    }


}