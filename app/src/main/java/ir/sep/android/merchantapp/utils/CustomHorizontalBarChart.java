package ir.sep.android.merchantapp.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.card.MaterialCardView;

import javax.inject.Inject;

import ir.sep.android.merchantapp.Const;
import ir.sep.android.merchantapp.R;
import ir.sep.android.merchantapp.data.entities.Rahsepar;

public class CustomHorizontalBarChart extends FrameLayout {

    public interface BarChartCallBack {
        void onclick(Rahsepar.Prcodenum prcodenum);
    }


    private Context context;
    private BarChartCallBack callBack;

    @Inject
    public CustomHorizontalBarChart(Context context, BarChartCallBack callBack, BarInfo barInfo) {
        super(context);
        this.callBack = callBack;
        this.context = context;
        init(barInfo);
    }


    void init(BarInfo barInfo) {
        inflate(getContext(), R.layout.horizontal_bar_chart_layout, this);

        MaterialCardView cardView = findViewById(R.id.cv_main);
        cardView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBack != null) {
                    callBack.onclick(barInfo.prcodenum);
                }
            }
        });

        TextView textView = findViewById(R.id.tv_title);
        textView.setText(barInfo.getTitle());

        NumberHelper numberHelper=new NumberHelper();

        TextView textViewPercentage = findViewById(R.id.tv_percentage);
        String genratePrecentage=numberHelper.isDecimalPartEqualToZero(String.valueOf(barInfo.getPercent())) ?
                String.valueOf(numberHelper.getIntPartOfFloat(barInfo.getPercent())): String.valueOf(barInfo.getPercent());

        textViewPercentage.setText(genratePrecentage + "%");

        TextView textViewPrice = findViewById(R.id.tv_price);
        textViewPrice.setText(numberHelper.sperateDigit(barInfo.getAmount()) + " " + this.context.getResources().getString(R.string.lbl_rial));

        TextView textViewCount = findViewById(R.id.tv_count);
        textViewCount.setText(String.format("  (%d)  ", barInfo.getCount()));



        float test=(float)  (int)barInfo.getPercent()/100.0f;
        ConstraintLayout layout=findViewById(R.id.layout);

        final View seekBar = findViewById(R.id.seekBar);
        ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) seekBar.getLayoutParams();

        if(test >=0.80 && test <=100)
        {
            test -= 0.2f ;
        }else if(test >=0.40 && test <=79)
        {
            test -= 0.2f ;
        }else if(test >=0.30 && test <=0.39)
        {
            test -= 0.15f ;
        }else if(test >=0.20 && test <=0.29)
        {
            test -= 0.15f ;
        }
        lp.matchConstraintPercentWidth = test;
        seekBar.setLayoutParams(lp);
        seekBar.setBackgroundColor(barInfo.getColor());

    }
    public static int getScreenWidth(Context context, ConstraintLayout mcLayout) {
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        return displayMetrics.widthPixels;



//determine display aspect ratio
        WindowManager wm = (WindowManager)
                context.getSystemService(Context.WINDOW_SERVICE);
        assert wm != null;
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        assert display != null;
        display.getMetrics(metrics);
        float width = metrics.widthPixels;
        float height = metrics.heightPixels;
        float ratio = width/height;

        return (int)width;
    }


    public static class BarInfo {
        private Rahsepar.Prcodenum prcodenum;

        private String title;
        private float percent;
        private int color;
        private long amount;
        private int count;


        public BarInfo(Rahsepar.Prcodenum prcodenum, String title, float percent, long amount, int count, int color) {
            this.prcodenum = prcodenum;
            this.title = title;
            this.percent = percent;
            this.color = color;
            this.amount = amount;
            this.count = count;

        }


        public Rahsepar.Prcodenum getPrcodenum() {
            return prcodenum;
        }

        public void setPrcodenum(Rahsepar.Prcodenum prcodenum) {
            this.prcodenum = prcodenum;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public float getPercent() {
            return percent;
        }

        public void setPercent(float percent) {
            this.percent = percent;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public long getAmount() {
            return amount;
        }

        public void setAmount(long amount) {
            this.amount = amount;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
