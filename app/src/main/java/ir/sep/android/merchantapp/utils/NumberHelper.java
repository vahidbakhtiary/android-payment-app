package ir.sep.android.merchantapp.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import javax.inject.Inject;
import javax.inject.Singleton;


public class NumberHelper {

    public String sperateDigit(long number) {
        DecimalFormat mFormat = new DecimalFormat("###,###,###,##0");
        return mFormat.format(number);
    }
    public boolean isDecimalPartEqualToZero(String value)
    {
        if (new BigDecimal(value).compareTo(BigDecimal.ZERO) == 0) {
            return true;
        }else
        {
            return false;
        }

    }

    public int getIntPartOfFloat(float number)
    {
        return (int) number;
    }
}
