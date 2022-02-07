package ir.sep.android.merchantapp.utils;

import android.util.Log;

import ir.sep.android.merchantapp.BuildConfig;

import static ir.sep.android.merchantapp.Const.TAG;

public class LogHelper {
    public static void log(Object message){
        if(BuildConfig.DEBUG){
            Log.d(TAG, message.toString());
        }
    }
}
