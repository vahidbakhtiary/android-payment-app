package ir.sep.android.merchantapp.utils

import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import ir.sep.android.merchantapp.R


class CustomSnackbar
@JvmOverloads
constructor(
        view: View,
        message: String,
        type: SnackbarType,
        buttonText: String = "",
        private val onClickListener: ((View) -> Unit)? = null
) {

    private val snackbar = Snackbar.make(view, message, BaseTransientBottomBar.LENGTH_LONG)

    init {
        ViewCompat.setLayoutDirection(snackbar.view, ViewCompat.LAYOUT_DIRECTION_RTL)

        when (type) {
            SnackbarType.Info ->
                snackbar.setBackgroundTint(ContextCompat.getColor(view.context, R.color.colorPrimaryDark))

            SnackbarType.Warning ->
                snackbar.setBackgroundTint(ContextCompat.getColor(view.context, R.color.colorPrimaryDark))

            SnackbarType.Error ->
                snackbar.setBackgroundTint(ContextCompat.getColor(view.context, R.color.colorRedDark))

        }

        if (buttonText.isNotEmpty()) {
            snackbar.duration = BaseTransientBottomBar.LENGTH_INDEFINITE
            snackbar.setActionTextColor(ContextCompat.getColor(view.context, R.color.colorAccent))
            snackbar.setAction(buttonText) {
                onClickListener?.invoke(it)
            }
        }

        snackbar.show()
    }

    fun dismiss() {
        snackbar.dismiss()
    }

    enum class SnackbarType {
        Info,
        Warning,
        Error
    }
}