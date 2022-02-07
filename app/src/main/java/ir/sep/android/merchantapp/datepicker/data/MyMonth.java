package ir.sep.android.merchantapp.datepicker.data;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class MyMonth {
    private int year;
    private int month;
    @NotNull private String monthName;
    private boolean isPrevious;
    private boolean isActive;

    public MyMonth(int year, int month, @NotNull String monthName, boolean isPrevious, boolean isActive) {
        this.year = year;
        this.month = month;
        this.monthName = monthName;
        this.isPrevious = isPrevious;
        this.isActive = isActive;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    @NotNull
    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(@NotNull String monthName) {
        this.monthName = monthName;
    }

    public boolean isPrevious() {
        return isPrevious;
    }

    public void setPrevious(boolean previous) {
        isPrevious = previous;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @NotNull
    @Override
    public String toString() {
        return monthName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyMonth myMonth = (MyMonth) o;
        return year == myMonth.year &&
                month == myMonth.month;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(year, month);
    }
}
