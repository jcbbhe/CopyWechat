package com.jcbbhe.wechat.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jcbbhe.wechat.service.LoadService

open class BaseActivity : AppCompatActivity() {

    companion object {
        var baseActivityContext: Context? = null
        var baseActivity: Activity? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseActivityContext = this
        baseActivity = this

        startService(Intent(applicationContext, LoadService::class.java))
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

    }
}
