package ir.sep.android.merchantapp.ui.dashboard;

import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import ir.sep.android.merchantapp.Const;
import ir.sep.android.merchantapp.R;
import ir.sep.android.merchantapp.utils.SharedPreferencesHelper;

@AndroidEntryPoint
public class MonthPickerDialog extends DialogFragment {

    @Inject
    DashboardViewModel dashboardViewModel;
    @Inject
    SharedPreferencesHelper sharedPreferencesHelper;

    private Button buttonOK,buttonCancel;
    private NumberPicker picker1;
    private String[] pickerVals;

   private String currecnt_month;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle mArgs = getArguments();
       currecnt_month = mArgs.getString("currecnt_month");

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        View view=inflater.inflate(R.layout.fragment_dialog_month_picker,null);

        buttonOK =view.findViewById(R.id.btnOK);
        buttonCancel =view.findViewById(R.id.btnCancel);

        picker1 = view.findViewById(R.id.numberpicker_main_picker);
        picker1.setMaxValue(11);
        picker1.setMinValue(0);
        pickerVals  = getResources().getStringArray(R.array.month_array);
        picker1.setDisplayedValues(pickerVals);
        picker1.setValue(getIndexOfMonthArray(currecnt_month));
        picker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                int valuePicker1 = picker1.getValue();
                Log.d("picker value", pickerVals[valuePicker1]);
                //Toast.makeText(getContext(), pickerVals[valuePicker1], Toast.LENGTH_SHORT).show();
            }
        });


        buttonCancel.setOnClickListener(v -> dismiss());
        buttonOK.setOnClickListener(v -> {
            try {
                int valuePicker1 = picker1.getValue();
                Log.d("picker value", pickerVals[valuePicker1]);
                int month = getIndexOfMonthArray(pickerVals[valuePicker1]) + 1;
                dashboardViewModel.today.setMonth(month);
                dashboardViewModel.setTodayShamsiDate(dashboardViewModel.today);
                dashboardViewModel.sendRequest(dashboardViewModel.makeTotallyRequestParameters(sharedPreferencesHelper.select(Const.SHARED_PREF_TERMINAL_NUMBER_KEY)));

            }catch (Exception ex)
            {

            }
            dismiss();
        });

        return  view;
    }


    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        Point size = new Point();

        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);

        int width = size.x;

        window.setLayout((int) (width * 0.75), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
    }

    private int getIndexOfMonthArray(String str)
    {
        List<String> list = Arrays.asList(pickerVals);
        int month = list.indexOf(str);

        return month;
    }
}
