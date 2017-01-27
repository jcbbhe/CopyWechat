package com.jcbbhe.wechat.tool

import android.Manifest
import android.text.TextUtils
import com.jcbbhe.wechat.Constant
import com.jcbbhe.wechat.Utils.CheckPermission
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by jcbbhe on 17/1/4.
 */
object LogsTool {

    fun getLogFileName(): String {
        val tempDate = SimpleDateFormat("yyyy-MM-dd")
        val ts = tempDate.format(Date()).toString()
        return "/$ts.log"
    }

    fun action(s: String) {
        if (!CheckPermission.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) return
        try {
            if (TextUtils.isEmpty(s)) return
            val path = Constant.Application_LogPath + getLogFileName()
            val f = File(path)
            if (!f.exists()) f.createNewFile()

            val ts = SimpleDateFormat("yyyy-MM-dd" + " " + "hh:mm:ss").format(Date()).toString()
            val fw = FileWriter(path, true)
            val bw = BufferedWriter(fw)

            bw.write(ts + "\t====\t" + s + "\n")
            bw.flush()
            bw.close()
            fw.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}