package com.jcbbhe.wechat.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.jcbbhe.wechat.Constant
import com.jcbbhe.wechat.R
import com.jcbbhe.wechat.tool.FileTool
import com.jcbbhe.wechat.http.HttpTool
//import com.jcbbhe.wechat.http.HttpTool.Callback
//import okhttp3.ResponseBody
//import retrofit2.Call
//import retrofit2.Response
import java.io.ByteArrayInputStream
import java.io.File


class LauncherActivity : AppCompatActivity() {
    val THREAD_SLEEP: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
        Thread {
            Thread.sleep(THREAD_SLEEP)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }.start()
    }
}

