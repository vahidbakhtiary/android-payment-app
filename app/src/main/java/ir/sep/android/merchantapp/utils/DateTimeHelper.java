package ir.sep.android.merchantapp.utils;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DateTimeHelper {

    @Inject
    public DateTimeHelper() {
    }

    public String DateToString(int year, int month, int day) {
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
