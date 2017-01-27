package com.jcbbhe.wechat

import android.os.Environment

/**
 * Created by jcbbhe on 16/12/31.
 */
object Constant {
    val SP_LOGIN_NAME = "10001"
    val System_RootPath = Environment.getExternalStorageDirectory().path!!
    val Application_RootPath = System_RootPath + "/CopyWechat/"
    val Application_ImagePath = Application_RootPath + "/images/"
    val Application_AudioPath = Application_RootPath + "/audio/"
    val Application_VideoPath = Application_RootPath + "/video/"
    val Application_FilePath = Application_RootPath + "/files/"
    val Application_LogPath = Application_RootPath + "/logs/"
    val Application_CachePath = Application_RootPath + "/cache/"
    val Application_EmojiPath = Application_RootPath + "/emoji/"

    //    http://www.51jkzx.cc//uploads
//    val Http_Base_Url = "https://www.baidu.com"
    val Http_Base_Url = "http://192.168.0.153:83/"
    val DataBase_Name = "test"

    val SUBSCRIBE_GET_STORAGE_WRITE = 10001                     //订阅获取SD卡权限的Tag
    val SUBSCRIBE_MESSAGE_SEND_SUCCESS = 10002                  //消息发送成功tag


}