package ir.sep.android.merchantapp.ui.faq;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ir.sep.android.merchantapp.Const;
import ir.sep.android.merchantapp.R;
import ir.sep.android.merchantapp.databinding.FragmentFaqBinding;
import ir.sep.android.merchantapp.databinding.FragmentLoginBinding;
import ir.sep.android.merchantapp.ui.base.BaseFragment;

public class FaqFragment extends BaseFragment {


    private FragmentFaqBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFaqBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.webView.setWebViewClient(new AppWebViewClients(Const.FAQ_BASEURL, binding.webView,binding.viewEmpty.getRoot(),binding.progressBar));
        binding.webView.loadUrl(Const.FAQ_BASEURL);
        binding.webView.getSettings().setJavaScriptEnabled(true);

    }



}
