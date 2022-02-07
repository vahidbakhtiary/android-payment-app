package ir.sep.android.merchantapp.ui.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.media.Image;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import dagger.hilt.android.AndroidEntryPoint;
import ir.sep.android.merchantapp.Const;
import ir.sep.android.merchantapp.R;
import ir.sep.android.merchantapp.utils.NetworkHelper;

@AndroidEntryPoint
public class BaseActivity extends AppCompatActivity {

    NetworkChangeReceiver receiver;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        receiver = new NetworkChangeReceiver(this);

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
               String token = instanceIdResult.getToken();
                Log.e(Const.TAG, "onSuccess: " + token );
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) {
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            View v = getCurrentFocus();
//            if (v instanceof EditText) {
//                Rect outRect = new Rect();
//                v.getGlobalVisibleRect(outRect);
//                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
//                    v.clearFocus();
//                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                    if (imm != null) {
//                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//                    }
//                }
//            }
//        }
//        return super.dispatchTouchEvent(event);
//    }




    class NetworkChangeReceiver extends BroadcastReceiver {

        public boolean isConnected = false;
        int status;
        AlertDialog alert;


        public NetworkChangeReceiver(Activity a) {


            final AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);

            View view= LayoutInflater.from(getBaseContext()).inflate(R.layout.internet_disconnect_layout,null);
            builder.setCancelable(false);
            builder.setView(view);
            alert = builder.create();
            ImageView imageView=view.findViewById(R.id.image);
            Glide.with(getBaseContext())
                    .asGif()
                    .load(R.drawable.wifi_gif_file)
                    .into(imageView);

        }

        @Override
        public void onReceive(final Context context, final Intent intent) {

            status = NetworkHelper.getConnectivityStatusString(context);

            switch (status) {
                case NetworkHelper.TYPE_MOBILE:
                case NetworkHelper.TYPE_WIFI:
                    isConnected = true;
                    break;
                case NetworkHelper.TYPE_NOT_CONNECTED:
                    isConnected = false;
                default:
                    isConnected = false;
            }

            if (isConnected) {
                alert.dismiss();
            } else {
                alert.show();
            }

        }
    }
}
