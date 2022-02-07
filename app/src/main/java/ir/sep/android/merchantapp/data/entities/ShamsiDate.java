package ir.sep.android.merchantapp.data.entities;

import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import saman.zamani.persiandate.PersianDate;

public class ShamsiDate {
    private int year;
    private int month;
    private int day;

    public ShamsiDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    //today
    public ShamsiDate() {
        PersianDate persianDate = new PersianDate();
        this.year = persianDate.getShYear();
        this.month = persianDate.getShMonth();
        this.day = persianDate.getShDay();
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

    public String getMonthName() {
        return new PersianDate()
                .setShYear(this.getYear())
                .setShMonth(this.getMonth())
                .setShDay(this.getDay())
                .monthName();
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonthLength(){
        return new PersianDate()
                .setShYear(this.getYear())
                .setShMonth(this.getMonth())
                .setShDay(this.getDay())
                .getMonthLength();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof ShamsiDate)) {
            return false;
        }
        ShamsiDate date = (ShamsiDate) obj;
        return date.year == getYear() && date.month == getMonth() && date.day == getDay();
    }

    @NotNull
    @Override
    public String toString() {
        if (day <= 9 || month <= 9) {
            if (day <= 9 && month <= 9) {
                return year + "/0" + month + "/0" + day;
            }
            if (day <= 9) {
                return year + "/" + month + "/0" + day;
            }
            return year + "/0" + month + "/" + day;
        } else {
            return year + "/" + month + "/" + day;
        }
    }
}
