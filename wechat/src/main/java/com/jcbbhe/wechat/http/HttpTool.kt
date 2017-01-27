package com.jcbbhe.wechat.http

import android.util.Log
import com.jcbbhe.wechat.Constant
import com.jcbbhe.wechat.tool.LogsTool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


/**
 * Created by jcbbhe on 17/1/4.
 */
object HttpTool {
    //
    private val responseRetrofit = Retrofit.Builder()
            .baseUrl(Constant.Http_Base_Url)
            .client(getOkHttpClient())
            .build()

    private val stringRetrofit = Retrofit.Builder()
            .baseUrl(Constant.Http_Base_Url)
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(getOkHttpClient())
            .build()

    private val gsonRetrofit = Retrofit.Builder()
            .baseUrl(Constant.Http_Base_Url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttpClient())
            .build()

    /**
     * 生成一个返回值为String类型的RetrofitService
     */
    fun Response(): RetrofitService {
        return responseRetrofit.create(RetrofitService::class.java)
    }

    /**
     * 生成一个返回值为String类型的RetrofitService
     */
    fun String(): RetrofitService {
        return stringRetrofit.create(RetrofitService::class.java)
    }

    /**
     * 生成一个返回值为Gson类型的RetrofitService
     */
    fun Gson(): RetrofitService {
        return gsonRetrofit.create(RetrofitService::class.java)
    }

    /**
     * 重写Retrofit的Callback
     * 定义一个公共的回调函数
     */
    open class Callback<T> : retrofit2.Callback<T> {
        override fun onResponse(call: Call<T>?, response: Response<T>?) {
        }

        override fun onFailure(call: Call<T>?, t: Throwable?) {
            Log.e("onFailure", t!!.toString())
        }
    }

    /**
     * 定义网络请求的拦截器
     */
    private fun getOkHttpClient(): OkHttpClient {
        //日志显示级别
        val level = HttpLoggingInterceptor.Level.BODY
        //新建log拦截器
        val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
            LogsTool.action(message)
        })
        loggingInterceptor.level = level
        //定制OkHttp
        val httpClientBuilder = OkHttpClient.Builder()
        //OkHttp进行添加拦截器loggingInterceptor
        httpClientBuilder.addInterceptor(loggingInterceptor)
        return httpClientBuilder.build()
    }
}