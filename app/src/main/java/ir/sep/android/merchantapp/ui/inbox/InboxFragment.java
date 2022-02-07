package ir.sep.android.merchantapp.ui.inbox;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import ir.sep.android.merchantapp.R;
import ir.sep.android.merchantapp.data.entities.Inbox;
import ir.sep.android.merchantapp.data.entities.MerchantServiceBaseEntity;
import ir.sep.android.merchantapp.data.entities.MerchantServiceRollEntity;
import ir.sep.android.merchantapp.databinding.FragmentInboxBinding;
import ir.sep.android.merchantapp.ui.base.BaseFragment;
import ir.sep.android.merchantapp.utils.CustomSnackbar;


@AndroidEntryPoint
public class InboxFragment extends BaseFragment {

    @Inject
    InboxViewModel viewModel;

    private FragmentInboxBinding binding;
    private InboxAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentInboxBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.updateToRead();
        setupAdapter();
        setupObserver(view);

//        Inbox inbox=new Inbox("ttt","desc1");
//        viewModel.insert(inbox);
//
//        viewModel.getAllInbox().observe(getViewLifecycleOwner(), new Observer<List<Inbox>>() {
//            @Override
//            public void onChanged(List<Inbox> inboxes) {
//                String s="";
//                String ss="";
//            }
//        });


    }


    private void setupAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        adapter = new InboxAdapter();
        binding.rvReport.setLayoutManager(layoutManager);
        binding.rvReport.setHasFixedSize(true);
        binding.rvReport.setAdapter(adapter);
    }
    private void setupObserver(View view) {

        viewModel.getAllInbox().observe(getViewLifecycleOwner(), new Observer<List<Inbox>>() {
            @Override
            public void onChanged(List<Inbox> inboxes) {

                if (inboxes.size()>0) {
                        adapter.submitList(inboxes);
                    binding.viewEmpty.getRoot().setVisibility(View.GONE);
                }

                binding.progressBar.setVisibility(View.GONE);
            }
        });

    }
}
