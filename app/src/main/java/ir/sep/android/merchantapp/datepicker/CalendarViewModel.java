package ir.sep.android.merchantapp.datepicker;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ir.sep.android.merchantapp.datepicker.data.MyDate;
import ir.sep.android.merchantapp.datepicker.data.MyDateProvider;
import ir.sep.android.merchantapp.datepicker.data.MyMonth;

public class CalendarViewModel extends ViewModel {


    private SavedStateHandle savedStateHandle;
    private MutableLiveData<MyMonth> activeMonth = new MutableLiveData<>(MyDateProvider.getCurrentMonth());
    private MutableLiveData<MyDate> activeDate = new MutableLiveData<>(MyDateProvider.getCurrentDate());

    @ViewModelInject
    public CalendarViewModel(@Assisted SavedStateHandle savedStateHandle) {
        this.savedStateHandle = savedStateHandle;
    }

    public LiveData<MyMonth> getActiveMonth() {
        return activeMonth;
    }

    public void setActiveMonth(MyMonth activeMonth) {
        if (this.activeMonth.getValue()==activeMonth) return;
        this.activeMonth.setValue(activeMonth);
        this.activeDate.setValue(new MyDate(activeMonth.getYear(), activeMonth.getMonth(), 1));
    }

    public LiveData<MyDate> getActiveDate() {
        return activeDate;
    }

    public void setActiveDate(MyDate date) {
        this.activeDate.setValue(date);
    }

    public LiveData<List<MyMonth>> getMonthsOfYear() {
        return Transformations.switchMap(activeDate, date -> {
            List<MyMonth> monthsOfYear = MyDateProvider.getMonthOfYear(date.getYear());
            for (MyMonth month : monthsOfYear) {
                month.setActive(month.getMonth() == date.getMonth());
            }
            return new MutableLiveData<>(monthsOfYear);
        });
    }
}
