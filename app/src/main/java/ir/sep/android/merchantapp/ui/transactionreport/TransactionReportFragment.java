package ir.sep.android.merchantapp.ui.transactionreport;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.zhuinden.livedatacombinetuplekt.LiveDataCombineTupleKt;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import ir.sep.android.merchantapp.Const;
import ir.sep.android.merchantapp.R;
import ir.sep.android.merchantapp.data.entities.Rahsepar;
import ir.sep.android.merchantapp.databinding.FragmentTransactionReportBinding;
import ir.sep.android.merchantapp.ui.transactionreport.adapter.TransactionReportListener;
import ir.sep.android.merchantapp.ui.transactionreport.adapter.TransactionReportAdapter;
import ir.sep.android.merchantapp.utils.DialogHelper;
import ir.sep.android.merchantapp.utils.JsonParserHelper;
import ir.sep.android.merchantapp.utils.SharedPreferencesHelper;

import static ir.sep.android.merchantapp.utils.LogHelper.*;


@AndroidEntryPoint
public class TransactionReportFragment extends Fragment implements TransactionReportListener {


    @Inject
    JsonParserHelper jsonParserHelper;

    @Inject
    DialogHelper dialogHelper;

    @Inject
    SharedPreferencesHelper sharedPreferencesHelper;

    @Inject
    TransactionReportViewModel viewModel;


    private FragmentTransactionReportBinding binding;

    private TransactionReportAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sendDefaultRequest();
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTransactionReportBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);


        setupAdapter();
        setupObserver();
    }

    private void sendDefaultRequest() {
        String termId = sharedPreferencesHelper.select(Const.SHARED_PREF_TERMINAL_NUMBER_KEY);
        viewModel.setparam(viewModel.makeRequestParam(termId));
    }

    private void setupAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        adapter = new TransactionReportAdapter(this);
        binding.rvReport.setLayoutManager(layoutManager);
        binding.rvReport.setHasFixedSize(true);
        binding.rvReport.setAdapter(adapter);
    }

    private void setupObserver() {
        viewModel.getLiveDataTransactionReport().observe(getViewLifecycleOwner(), rahseparResponse -> {

            if (rahseparResponse != null) {
                if (rahseparResponse.getSuccess()) {
                    if(rahseparResponse.getCount()==0)
                    {
                        binding.viewEmpty.getRoot().setVisibility(View.VISIBLE);
                        adapter.submitList(null);
                        return;
                    }
                    try {
                        viewModel.setRahseparItemsInList(getRahseparList(rahseparResponse.getData()));
                    } catch (Exception e) {
                        dialogHelper.show(getView(), DialogHelper.DialogType.Error, getString(R.string.alert_faild_message));
                    }
                } else {
                    dialogHelper.show(getView(), DialogHelper.DialogType.Error, rahseparResponse.getMessage());
                }
            } else {
                dialogHelper.show(getView(), DialogHelper.DialogType.Error, getString(R.string.alert_faild_call_webservice));
            }
        });

        //loading state
        viewModel.getTransactionReportLoadingState().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading){
                binding.viewEmpty.getRoot().setVisibility(View.GONE);
                binding.progressBar.setVisibility(View.VISIBLE);
                //scroll to top
                binding.rvReport.scrollToPosition(0);
                //shrink last expanded item
                for (Rahsepar item : adapter.getCurrentList()) {
                    if (item.isExpanded()) {
                        viewModel.setExpandedItemInList(null);
                        adapter.notifyItemChanged(item.getPositionInList());
                    }
                }
            }else {
                binding.progressBar.setVisibility(View.GONE);
            }
        });

        //adapter logic
        LiveDataCombineTupleKt.combineTuple(viewModel.getExpandedItemInList(), viewModel.getRahseparItemsInList())
                .observe(getViewLifecycleOwner(), result -> {

                    @Nullable
                    Rahsepar newExpandedItem = result.component1();
                    List<Rahsepar> newList = result.component2();


                    if (newList==null) {
//                        binding.viewEmpty.getRoot().setVisibility(View.VISIBLE);
                        return;
                    }

                    //there is no item in new list
                    if (newList.size() == 0) {
                        binding.viewEmpty.getRoot().setVisibility(View.VISIBLE);
                        adapter.submitList(null);
                        log("list is updated");
                        return;
                    }

                    binding.viewEmpty.getRoot().setVisibility(View.GONE);

                    //sync newList with newExpandedItem
                    for (Rahsepar item : newList) {
                        item.setExpanded(item.equals(newExpandedItem));
                    }

                    //the old list was empty but new list has some item
                    if (adapter.getCurrentList().size() == 0) {
                        adapter.submitList(null);
                        adapter.submitList(newList);
                        log("list is updated");
                        return;
                    }

                    //the old list and new list are totally different
                    if (!adapter.getCurrentList().equals(newList) || adapter.getCurrentList().size() != newList.size()) {
                        adapter.submitList(null);
                        adapter.submitList(newList);
                        log("list is updated");
                        return;
                    }

                    //lists are equals, update ExpandedItem
                    if (adapter.getCurrentList().equals(newList) && newExpandedItem != null) {
                        adapter.submitList(newList);
                        adapter.notifyItemChanged(newExpandedItem.getPositionInList());
                        //this way when user click last item, recyclerView will scroll to that item
                        //not necessary, so if it make any problem delete it
                        new Handler(Looper.getMainLooper()).postDelayed(() -> {
                            if (newExpandedItem.getPositionInList() == newList.size()-1){
                                binding.rvReport.scrollToPosition(newExpandedItem.getPositionInList());
                            }
                        }, 50);

                        log("list is updated");
                    }

                });
    }

    List<Rahsepar> getRahseparList(String json) throws Exception {
        return jsonParserHelper.convertJsonToObject(Rahsepar.class, json);
    }

    @Override
    public void onMoreClick(Rahsepar rahsepar) {
        //shrink last expanded item
        for (Rahsepar item : adapter.getCurrentList()) {
            if (item.isExpanded()) {
                adapter.notifyItemChanged(item.getPositionInList());
            }
        }
        if (rahsepar.isExpanded()) {
            viewModel.setExpandedItemInList(null);
            return;
        }
        viewModel.setExpandedItemInList(rahsepar);
    }
}