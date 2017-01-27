package com.jcbbhe.doctor.bean

import android.graphics.drawable.Drawable

/**
 * Created by jcbbhe on 17/1/2.
 */
class ChatToolbarPlus(s: String, d: Drawable? = null,k:String? = null) {

    var key:String? = null
    var itemImageDrawable: Drawable? = null
    var itemText: String? = null

    init {
        itemText = s
        itemImageDrawable = d
        key = k
    }
}