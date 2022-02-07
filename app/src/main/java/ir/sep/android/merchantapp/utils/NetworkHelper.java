package ir.sep.android.merchantapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkHelper {
    public static final int TYPE_WIFI = 1;
    public static final int TYPE_MOBILE = 2;
    public static final int TYPE_NOT_CONNECTED = 0;

    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    public static int getConnectivityStatusString(Context context) {
        int conn = NetworkHelper.getConnectivityStatus(context);
        String status = null;
        if (conn == NetworkHelper.TYPE_WIFI) {
           //"Wifi enabled";
            return NetworkHelper.TYPE_WIFI;
        } else if (conn == NetworkHelper.TYPE_MOBILE) {
            //"Mobile data enabled";
            return NetworkHelper.TYPE_MOBILE;
        } else if (conn == NetworkHelper.TYPE_NOT_CONNECTED) {
            //"Not connected to Internet";
            return NetworkHelper.TYPE_NOT_CONNECTED;
        }
        return NetworkHelper.TYPE_NOT_CONNECTED;
    }
}