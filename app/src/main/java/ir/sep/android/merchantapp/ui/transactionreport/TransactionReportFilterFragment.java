package ir.sep.android.merchantapp.ui.transactionreport;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.alirezaafkar.sundatepicker.DatePicker;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import ir.sep.android.merchantapp.Const;
import ir.sep.android.merchantapp.R;
import ir.sep.android.merchantapp.data.entities.Rahsepar;
import ir.sep.android.merchantapp.data.entities.ShamsiDate;
import ir.sep.android.merchantapp.databinding.FragmentTransactionFilterBinding;
import ir.sep.android.merchantapp.ui.base.BaseFragment;
import ir.sep.android.merchantapp.utils.SharedPreferencesHelper;

@AndroidEntryPoint
public class TransactionReportFilterFragment extends BaseFragment {

    @Inject
    TransactionReportViewModel transactionReportViewModel;
    @Inject
    SharedPreferencesHelper sharedPreferencesHelper;
    private FragmentTransactionFilterBinding binding;
    private NavController navController;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTransactionFilterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        //todo when terminalId logic specified move it to viewModel
        String termId = sharedPreferencesHelper.select(Const.SHARED_PREF_TERMINAL_NUMBER_KEY);
        binding.etTerminalNumber.setText(termId);

        setupDateButtons();
        setupAutoCompleteTextViews();

        binding.btnFilter.setOnClickListener(v -> filter());
        binding.btnDefault.setOnClickListener(v -> setDefaultValues());

    }

    private void filter() {
        if (TextUtils.isEmpty(binding.etTerminalNumber.getText())) {
            binding.tilTerminalNumber.setError("شماره ترمینال اجباری می باشد");
            return;
        }
        //make request
        String termId = binding.etTerminalNumber.getText().toString();
        transactionReportViewModel.setparam(transactionReportViewModel.makeRequestParam(termId));

        transactionReportViewModel.setparamTotally(transactionReportViewModel.makeTotallyRequestParam(termId));
        //back to report
        navController.navigateUp();
    }

    private void setDefaultValues() {
        transactionReportViewModel.setupDefaultRequest();
        //update ui
        String termId = sharedPreferencesHelper.select(Const.SHARED_PREF_TERMINAL_NUMBER_KEY);
        binding.etTerminalNumber.setText(termId);

        binding.etAutoPosconditioncode.setText("");
        binding.etAutoPrcode.setText("");
        binding.etAutoShaparakstatus.setText("");
        binding.etAutoSettle.setText("");
    }


    private void setupAutoCompleteTextViews() {
        List<String> list = new ArrayList<>();

        Rahsepar.Settlestatus[] values = Rahsepar.Settlestatus.values();
        for (Rahsepar.Settlestatus item : values) {
            list.add(item.getDescription());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                R.layout.item_simple_spinner,
                list);
        binding.etAutoSettle.setInputType(0);
        binding.etAutoSettle.setAdapter(adapter);
        binding.etAutoSettle.setText(transactionReportViewModel.getSettlestatus().getValue(),false);
        binding.etAutoSettle.setOnItemClickListener((parent, view, position, rowId) -> {
            String selection = (String)parent.getItemAtPosition(position);
            transactionReportViewModel.setSettlestatus(selection);
        });


        list = new ArrayList<>();
        Rahsepar.Shaparakstatus[] shaparakstatuses = Rahsepar.Shaparakstatus.values();
        for (Rahsepar.Shaparakstatus item : shaparakstatuses) {
            list.add(item.getDescription());
        }

        adapter = new ArrayAdapter<>(
                requireContext(),
                R.layout.item_simple_spinner,
                list);
        binding.etAutoShaparakstatus.setInputType(0);
        binding.etAutoShaparakstatus.setAdapter(adapter);
        binding.etAutoShaparakstatus.setText(transactionReportViewModel.getShaparakstatus().getValue(),false);
        binding.etAutoShaparakstatus.setOnItemClickListener((parent, view, position, rowId) -> {
            String selection = (String)parent.getItemAtPosition(position);
            transactionReportViewModel.setShaparakstatus(selection);
        });


        list = new ArrayList<>();
        Rahsepar.Prcodenum[] valuesPrcodenums = Rahsepar.Prcodenum.values();
        for (Rahsepar.Prcodenum item : valuesPrcodenums) {
            list.add(item.getDescription());
        }

        adapter = new ArrayAdapter<>(
                requireContext(),
                R.layout.item_simple_spinner,
                list);
        binding.etAutoPrcode.setInputType(0);
        binding.etAutoPrcode.setAdapter(adapter);
        binding.etAutoPrcode.setText(transactionReportViewModel.getPrcodenum().getValue(),false);
        binding.etAutoPrcode.setOnItemClickListener((parent, view, position, rowId) -> {
            String selection = (String)parent.getItemAtPosition(position);
            transactionReportViewModel.setPrcodenum(selection);
        });


        list = new ArrayList<>();
        Rahsepar.Posconditioncode[] posconditioncodes = Rahsepar.Posconditioncode.values();
        for (Rahsepar.Posconditioncode item : posconditioncodes) {
            list.add(item.getDescription());
        }

        adapter = new ArrayAdapter<>(
                requireContext(),
                R.layout.item_simple_spinner,
                list);
        binding.etAutoPosconditioncode.setInputType(0);
        binding.etAutoPosconditioncode.setAdapter(adapter);
        binding.etAutoPosconditioncode.setText(transactionReportViewModel.getPosconditioncode().getValue(),false);
        binding.etAutoPosconditioncode.setOnItemClickListener((parent, view, position, rowId) -> {
            String selection = (String)parent.getItemAtPosition(position);
            transactionReportViewModel.setPosconditioncode(selection);
        });

    }

    private void setupDateButtons() {
        binding.etStartDate.setOnClickListener(v -> {
            ShamsiDate maxStartDate = transactionReportViewModel.getMaxStartDate();
            ShamsiDate startDate = transactionReportViewModel.getStartDate().getValue();
            if (startDate == null) startDate = transactionReportViewModel.getToday();
            new DatePicker.Builder()
                    .setRetainInstance(true)
                    .date(startDate.getDay(), startDate.getMonth(), startDate.getYear())
                    .maxDate(maxStartDate.getYear(), maxStartDate.getMonth(), maxStartDate.getDay())
                    .build((id, calendar, day, month, year) -> {
                        transactionReportViewModel.setStartDate(year, month, day);
                        transactionReportViewModel.resetEndDate();
                    })
                    .show(getChildFragmentManager(), "DatePicker");
        });


        binding.etEndDate.setOnClickListener(v -> {
            ShamsiDate maxEndDate = transactionReportViewModel.getMaxEndDate();
            ShamsiDate minDate = transactionReportViewModel.getStartDate().getValue();
            ShamsiDate endDate = transactionReportViewModel.getEndDate().getValue();
            if (maxEndDate == null || minDate == null || endDate == null) return;
            new DatePicker.Builder()
                    .date(endDate.getDay(), endDate.getMonth(), endDate.getYear())
                    .setRetainInstance(true)
                    .minDate(maxEndDate.getYear(), minDate.getMonth(), minDate.getDay())
                    .maxDate(maxEndDate.getYear(), maxEndDate.getMonth(), maxEndDate.getDay())
                    .build((id, calendar, day, month, year) -> {
                        transactionReportViewModel.setEndDate(year, month, day);
                    })
                    .show(getChildFragmentManager(), "DatePicker");

        });


        transactionReportViewModel.getStartDate().observe(getViewLifecycleOwner(), shamsiDate -> {
            binding.etStartDate.setText(shamsiDate.toString());
        });
        transactionReportViewModel.getEndDate().observe(getViewLifecycleOwner(), shamsiDate -> binding.etEndDate.setText(shamsiDate.toString()));

    }
}