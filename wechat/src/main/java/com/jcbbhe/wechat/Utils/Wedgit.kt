package com.jcbbhe.wechat.Utils

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import com.jcbbhe.wechat.Wechat


/**
 * Created by jcbbhe on 17/1/4.
 */
object Wedgit {

    private var progressDialog: ProgressDialog? = null


    fun alert(s: String) {
        AlertDialog.Builder(Wechat.context).setTitle("温馨提示").setMessage(s).create().show()
    }

    fun createLoading() {
        progressDialog = ProgressDialog(Wechat.context)
        progressDialog!!.show()
    }

    fun dismissLoading() {
        progressDialog?.dismiss()
    }


}