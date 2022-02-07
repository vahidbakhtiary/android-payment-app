package ir.sep.android.merchantapp.datepicker.data;

import java.util.ArrayList;
import java.util.List;

import saman.zamani.persiandate.PersianDate;

public class MyDateProvider {

    public static List<MyMonth> getMonthOfYear(int activeYear) {
        List<MyMonth> monthList = new ArrayList<>();
        PersianDate persianDate = new PersianDate();
        int currentYear = persianDate.getShYear();

        //current year
        if (activeYear == currentYear) {
            int currentMonth = persianDate.getShMonth();
            for (int monthId = 1; monthId < currentMonth; monthId++) {
                monthList.add(new MyMonth(activeYear,
                        monthId,
                        persianDate.monthName(monthId),
                        true,
                        false));
            }

            monthList.add(new MyMonth(activeYear,
                    currentMonth,
                    persianDate.monthName(currentMonth),
                    false,
                    true));

            for (int month = currentMonth + 1; month <= 12; month++) {
                monthList.add(new MyMonth(activeYear,
                        month,
                        persianDate.monthName(month),
                        false,
                        false));
            }
        }

        //future year
        if (activeYear > currentYear) {
            for (int month = 1; month <= 12; month++) {
                monthList.add(new MyMonth(activeYear,
                        month,
                        persianDate.monthName(month),
                        false,
                        false));
            }
            monthList.get(0).setActive(true);
        }

        //previous year
        if (activeYear < currentYear) {
            for (int month = 1; month <= 12; month++) {
                monthList.add(new MyMonth(activeYear,
                        month,
                        persianDate.monthName(month),
                        true,
                        false));
            }
            monthList.get(0).setActive(true);
        }
        return monthList;
    }

    public static MyMonth getCurrentMonth() {
        PersianDate persianDate = new PersianDate();
        return new MyMonth(persianDate.getShYear(), persianDate.getShMonth(), persianDate.monthName(), false, true);
    }

    public static Integer getCurrentYear() {
        return new PersianDate().getShYear();
    }

    public static MyDate getCurrentDate() {
        PersianDate persianDate = new PersianDate();
        return new MyDate(persianDate.getShYear(),persianDate.getShMonth(),persianDate.getShDay());
    }
}
