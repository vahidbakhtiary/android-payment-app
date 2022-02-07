package ir.sep.android.merchantapp.ui.base;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import ir.sep.android.merchantapp.Const;
import ir.sep.android.merchantapp.R;
import ir.sep.android.merchantapp.ui.login.LoginFragment;
import ir.sep.android.merchantapp.ui.login.SmsFragment;
import ir.sep.android.merchantapp.utils.DialogHelper;
import ir.sep.android.merchantapp.utils.SharedPreferencesHelper;

@AndroidEntryPoint
public class BaseFragment extends Fragment {

//    String[] arrayFragment = {LoginFragment.class.getSimpleName(), SmsFragment.class.getSimpleName()};

    @Inject
    DialogHelper dialogHelper;

    @Inject
    SharedPreferencesHelper sharedPreferencesHelper;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        todo do not use this anymore
//        authriation(view);
    }

//    private void authriation(View view) {
//        NavController navController = Navigation.findNavController(view);
//        String isAuthorize = sharedPreferencesHelper.select(Const.SHARED_PREF_IS_AUTHORIZED_KEY);
//        if (TextUtils.isEmpty(isAuthorize)) {
//            for (String item : arrayFragment) {
//                if (item.equals(this.getClass().getSimpleName())) {
//                    return;
//                }
//            }
//
//            navController.navigate(R.id.loginFragment);
//
//        } else {
//            for (String item : arrayFragment) {
//                if (item.equals(this.getClass().getSimpleName())) {
//                    navController.navigate(R.id.dashboardFragment);
//                }
//            }
//        }
//    }


    public boolean isValidateEditText(EditText... params) {
        for (EditText item : params) {
            if (TextUtils.isEmpty(item.getText()))
                return false;
        }
        return true;
    }


    public class AppWebViewClients extends WebViewClient {
        private ProgressBar progressBar;
        private WebView webView;
        private View viewEmpty;
        String url;


        public AppWebViewClients(String url, WebView webView, View viewEmpty, ProgressBar progressBar) {
            this.url = url;
            this.progressBar = progressBar;
            this.webView = webView;
            this.viewEmpty = viewEmpty;

            this.webView.setVisibility(View.VISIBLE);
            this.viewEmpty.setVisibility(View.GONE);
            this.progressBar.setVisibility(View.VISIBLE);

            this.viewEmpty.findViewById(R.id.btn_refresh).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    webView.setWebViewClient(new AppWebViewClients(url, webView, viewEmpty, progressBar));
                    webView.loadUrl(url);
                    webView.getSettings().setJavaScriptEnabled(true);
                }
            });

        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            webView.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            super.onReceivedHttpError(view, request, errorResponse);

            webView.setVisibility(View.GONE);
            viewEmpty.setVisibility(View.VISIBLE);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);

        }
    }
}
