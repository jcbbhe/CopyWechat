package com.jcbbhe.wechat

import android.app.Application
import android.content.Context
import android.content.Intent
import com.hyphenate.chat.EMClient
import com.jcbbhe.wechat.service.LoadService
import com.hyphenate.chat.EMOptions
import com.hyphenate.EMCallBack
import android.R.attr.password
import android.util.Log


/**
 * Created by jcbbhe on 16/12/31.
 */
class Wechat : Application() {


    companion object {
        var context: Context? = null
    }

    override fun onCreate() {
        context = applicationContext
        super.onCreate()
        val options = EMOptions()
        options.acceptInvitationAlways = false
        EMClient.getInstance().init(applicationContext, options)
        EMClient.getInstance().setDebugMode(false)
        EMClient.getInstance().login("1", "123456", object : EMCallBack {
            //回调
            override fun onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups()
                EMClient.getInstance().chatManager().loadAllConversations()
                Log.d("main", "登录聊天服务器成功！")
            }
            override fun onProgress(progress: Int, status: String) {
            }
            override fun onError(code: Int, message: String) {
                Log.d("main", "登录聊天服务器失败！")
            }
        })
    }
}