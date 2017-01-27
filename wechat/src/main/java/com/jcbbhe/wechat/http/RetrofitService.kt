package com.jcbbhe.wechat.http

import com.jcbbhe.wechat.bean.EmojiBean
import com.jcbbhe.wechat.bean.UserBean
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

//import retrofit2.Call
//import retrofit2.http.GET
//import retrofit2.http.Url
//import okhttp3.ResponseBody


/**
 * Created by jcbbhe on 17/1/4.
 */
interface RetrofitService {

    @GET
    fun testGet(@Url url: String): Call<String>

    @GET
    fun downloadImage(@Url url: String): Call<ResponseBody>

    @GET
    fun getEmojiRes(@Url url:String): Call<String>


    @GET
    fun testGson(@Url url:String): Call<List<EmojiBean>>

    @GET("friendList")
    fun getFriendList():Call<List<UserBean>>

}