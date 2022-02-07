package ir.sep.android.merchantapp.utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class PrecentageCalculator {

    private double total = 0;

    public PrecentageCalculator(double total) {
        this.total = total;
    }


    public float calcPrecentage(int obtained)
    {
        if(obtained==0)
            return 0;

        float result= (float) (obtained * 100 / total);
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        String newFormat = df.format(result);

       return Float.parseFloat(newFormat);


    }
}
