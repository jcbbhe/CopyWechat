package com.jcbbhe.common.bean

/**
 * Created by jcbbhe on 17/1/13.
 */
class VoiceRecorderFile(p:String? = null,s:Float? = null,l:Int? = null) {

    val filePath = p
    val fileSize = s      //录音文件大小，单位：kb
    val fileLength = l    //录音文件长度，单位：s




}