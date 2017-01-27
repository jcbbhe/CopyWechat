package com.jcbbhe.wechat.tool

import com.jcbbhe.wechat.Constant

/**
 * Created by jcbbhe on 17/1/14.
 */
object Tools {

    fun getImagePath(s: String): String {
        return Constant.Http_Base_Url + s
    }


}