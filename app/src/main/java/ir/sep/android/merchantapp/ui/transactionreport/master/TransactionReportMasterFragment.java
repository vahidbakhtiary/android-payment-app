package ir.sep.android.merchantapp.ui.transactionreport.master;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import ir.sep.android.merchantapp.R;
import ir.sep.android.merchantapp.databinding.FragmentTransactionMasterReportBinding;
import ir.sep.android.merchantapp.ui.base.BaseFragment;
import ir.sep.android.merchantapp.ui.transactionreport.TotallyTransactionReportFragment;
import ir.sep.android.merchantapp.ui.transactionreport.TransactionReportFragment;

@AndroidEntryPoint
public class TransactionReportMasterFragment extends BaseFragment implements TotallyTransactionReportFragment.TabCallback {





    FragmentTransactionMasterReportBinding binding;

    @Inject
    TotallyTransactionReportFragment totallyTransactionReportFragment;

    @Inject
    TransactionReportFragment transactionReportFragment;

    private NavController navController;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTransactionMasterReportBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        setHasOptionsMenu(true);

        setupTabs();
    }

    private void setupTabs() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(requireActivity());
        viewPagerAdapter.addFragment(totallyTransactionReportFragment, getString(R.string.lbl_totally_transaction_report));
        viewPagerAdapter.addFragment(transactionReportFragment, getString(R.string.lbl_transaction_report));
        binding.viewPager.setAdapter(viewPagerAdapter);
        new TabLayoutMediator(binding.tabLayout, binding.viewPager,
                (tab, position) -> tab.setText(viewPagerAdapter.getPageTitle(position))
        ).attach();
    }

    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.filter_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_filter:
                if (transactionReportFragment.isVisible()) {
                    navController.navigate(R.id.action_transactionReportMasterFragment_to_transactionReportFilterFragment);
                } else {
                    navController.navigate(R.id.action_transactionReportMasterFragment_to_totallyTransactionReportFilterFragment);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setAnotherTab() {

        binding.viewPager.setCurrentItem(1);
    }
}
