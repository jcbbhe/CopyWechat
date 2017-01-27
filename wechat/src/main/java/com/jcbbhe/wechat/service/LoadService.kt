package com.jcbbhe.wechat.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.jcbbhe.wechat.Constant
import com.jcbbhe.wechat.Utils.RxBus
import com.jcbbhe.wechat.bean.EmojiBean
import com.jcbbhe.wechat.http.HttpTool
import retrofit2.Call
import retrofit2.Response
import rx.Observable
import java.io.File


/**
 * Created by jcbbhe on 17/1/4.
 */
class LoadService : Service() {

    companion object {
        val Action = "LoadService"
    }

    private var createFolder: Observable<String>? = null
    private val appPathArray = arrayOf(Constant.Application_RootPath, Constant.Application_ImagePath, Constant.Application_AudioPath,
            Constant.Application_VideoPath, Constant.Application_FilePath, Constant.Application_LogPath,
            Constant.Application_CachePath, Constant.Application_EmojiPath)

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
//        HttpTool.Gson().testGson("emoji").enqueue(object : HttpTool.Callback<List<EmojiBean>>() {
//            override fun onResponse(call: Call<List<EmojiBean>>?, response: Response<List<EmojiBean>>?) {
//                for (item in response!!.body()) {
//                    Log.i("wechat-onResponse", item.name)
//                }
//            }
//        })
        createAppRooPath()
        createFolder = RxBus.get().register(Constant.SUBSCRIBE_GET_STORAGE_WRITE)
        createFolder!!.subscribe {
            createAppRooPath()
        }


//        HttpTool.Gson().getEmojiRes("emoji").equals(object : HttpTool.Callback<String>() {
//            override fun onResponse(call: Call<String>?, response: Response<String>?) {
//                Log.e("emoji",response?.body().toString())
//            }
//
//            override fun onFailure(call: Call<String>?, t: Throwable?) {
//                Log.e("emoji","error")
//            }
//        })

//        val rs = HttpTool.Response()
//        rs.downloadImage(item).enqueue(downloadFileCallback)
    }

//    object downloadFileCallback : HttpTool.Callback<ResponseBody>() {
//        val emoji = DaoTool.getDefault().getDaoSession().emojiBeanDao
//        override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
//            if (response!!.code() == 200) {
//                val osba = FileTool.getByteArrayOutputStream(response!!.body().byteStream())
//                val filePath = FileTool.getInputMD5(ByteArrayInputStream(osba.toByteArray()))
//                val s = FileTool.saveInputStream(ByteArrayInputStream(osba.toByteArray()), Constant.Application_EmojiPath + "/$filePath")
////                if (s)
////                    emoji.insert(EmojiBean(filePath, Constant.Application_EmojiPath + "/$filePath"))
//            }
//        }
//    }


    /**
     * 初始化所需的文件夹
     */
    fun createAppRooPath() {
        appPathArray
                .map(::File)
                .filterNot(File::exists)
                .forEach { it.mkdirs() }
    }

    override fun onDestroy() {
        super.onDestroy()
        RxBus.get().unregister(Constant.SUBSCRIBE_GET_STORAGE_WRITE, createFolder!!)
    }
}