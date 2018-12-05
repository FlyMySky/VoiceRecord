package com.skwen.voicerecord.baselib.tools

import android.content.Context
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.Toast

object ToastUtils {


    fun showToast(context: Context, string: String) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
    }

    fun showToast(context: Context, @StringRes stringId: Int) {
        Toast.makeText(context, stringId, Toast.LENGTH_SHORT).show()
    }

    fun showSnackbar(view: View, string: String) {
        Snackbar.make(view, string, Snackbar.LENGTH_SHORT).show()
    }

    fun showSnackbar(view: View, @StringRes stringId: Int) {
        Snackbar.make(view, stringId, Snackbar.LENGTH_SHORT).show()
    }

    fun showSnackbar(view: View, string: String, actionName: String, listener: View.OnClickListener) {
        Snackbar.make(view, string, Snackbar.LENGTH_SHORT).setAction(actionName, listener).show()
    }

}