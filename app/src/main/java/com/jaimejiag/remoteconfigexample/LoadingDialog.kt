package com.jaimejiag.remoteconfigexample

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager

class LoadingDialog(context: Context) : AlertDialog(context) {

    init {
        setCancelable(false)
    }


    private fun config() {
        val params = window?.attributes
        params?.width = WindowManager.LayoutParams.MATCH_PARENT
        params?.height = WindowManager.LayoutParams.MATCH_PARENT
        window?.attributes = params
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }


    fun showLoading() {
        show()
        config()
        setContentView(R.layout.progress_dialog)
    }
}