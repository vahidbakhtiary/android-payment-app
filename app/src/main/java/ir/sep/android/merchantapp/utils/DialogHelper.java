package ir.sep.android.merchantapp.utils;

import android.view.View;

import androidx.core.view.ViewCompat;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;
import javax.inject.Singleton;

import ir.sep.android.merchantapp.R;

@Singleton
public class DialogHelper {

    @Inject
    public DialogHelper() {
    }

    public void show(View view, DialogHelper.DialogType dialogType, String message) {
        Snackbar snackbar= Snackbar.make(view, message, BaseTransientBottomBar.LENGTH_LONG);
        ViewCompat.setLayoutDirection(snackbar.getView(), ViewCompat.LAYOUT_DIRECTION_RTL);
        snackbar.setBackgroundTint(view.getContext().getResources().getColor(R.color.colorPrimaryDark));
        snackbar.show();
    }

    public void dismiss() {

    }

    public enum DialogType {
        Info,
        Warning,
        Error
    }


}
