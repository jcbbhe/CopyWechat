package com.jcbbhe.wechat.tool

import android.util.Log
import com.hyphenate.EMCallBack
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMMessage
import com.jcbbhe.wechat.Constant
import com.jcbbhe.wechat.Utils.RxBus

/**
 * Created by jcbbhe on 17/1/14.
 */
object EMessage {

    fun sendText(c: String, s: String) {
        val message = EMMessage.createTxtSendMessage(c, s)
        EMClient.getInstance().chatManager().sendMessage(message)
        message.setMessageStatusCallback(MessageCallback())
    }

    fun sendVoice(p: String, t: String? = null, l: Int, s: String) {
        val message = EMMessage.createVideoSendMessage(p, p, l, s)
        EMClient.getInstance().chatManager().setVoiceMessageListened(message)
        message.setMessageStatusCallback(MessageCallback())
    }

    fun sendImsage(i:String,c:Boolean,s:String) {
        val message = EMMessage.createImageSendMessage(i, c, s)
        EMClient.getInstance().chatManager().sendMessage(message)
        message.setMessageStatusCallback(MessageCallback())
    }


    class MessageCallback : EMCallBack {
        override fun onSuccess() {
            RxBus.get().post(Constant.SUBSCRIBE_MESSAGE_SEND_SUCCESS,"success")
        }

        override fun onProgress(p0: Int, p1: String?) {
            Log.i("sendMessage","onProgress")
        }

        override fun onError(p0: Int, p1: String?) {
            Log.i("sendMessage","onError")
        }

    }
}