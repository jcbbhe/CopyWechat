package com.jcbbhe.common.bean

import android.graphics.drawable.Drawable

/**
 * Created by jcbbhe on 17/1/3.
 */
open class ChatToolbarEmoticons {

    var id: Int = 0
    var name: String? = null
    var pid: Int = 0
    var key: String? = null



//    constructor(n: String, i: Drawable, l: List<Emoticon>?) {
//        emoticonName = n
//        icon = i
//        emoticons = l
//    }
//
//    constructor()
//
//    var emoticonName: String? = null
//    var icon: Drawable? = null
//    var emoticons: List<Emoticon>? = null


    open class Emoticon {
        var emoticonName: String? = null
        var icon: Drawable? = null
    }

}