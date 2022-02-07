package ir.sep.android.merchantapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.core.content.ContextCompat;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.qualifiers.ApplicationContext;
import ir.sep.android.merchantapp.Const;

import static android.content.Context.MODE_PRIVATE;

@Singleton
public class SharedPreferencesHelper {

    private SharedPreferences sharedPreferences;

    @Inject
    public SharedPreferencesHelper(@ApplicationContext Context context)
    {
        Log.e(Const.TAG, "SharedPreferencesHelper this: " + this );
        Log.e(Const.TAG, "SharedPreferencesHelper context: " + context );
        sharedPreferences=context.getSharedPreferences(Const.SHARED_PREF_merchant_config, MODE_PRIVATE);
    }

    public void insert(String key,String value)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,value);
        editor.commit();
    }
    public String select(String key)
    {
        if (sharedPreferences.contains(key))
        {
           return sharedPreferences.getString(key,"");
        }

        return "";
    }
}
