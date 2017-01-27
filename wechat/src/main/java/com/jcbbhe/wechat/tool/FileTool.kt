package com.jcbbhe.wechat.tool

import android.util.Log
import com.jcbbhe.wechat.Constant
//import okhttp3.ResponseBody
import java.io.*
import java.math.BigInteger
import java.security.MessageDigest
import java.util.*


/**
 * Created by jcbbhe on 17/1/4.
 */
object FileTool {

    /**
     * 将InputStream写入sd卡
     * 返回boolean
     */
    fun saveInputStream(inputStream: InputStream, p: String): Boolean {
        var outputStream: OutputStream? = null
        try {
            val fileReader = ByteArray(4096)
            val f = File(p)
            if (f.exists()) return true
//            val fileSize = body.contentLength()
//            var fileSizeDownloaded: Long = 0
            outputStream = FileOutputStream(f)
            while (true) {
                val read = inputStream.read(fileReader)
                if (read == -1)
                    break
                outputStream.write(fileReader, 0, read)
//                fileSizeDownloaded += read.toLong()
//                Log.d("writeResponseBodyToDisk", "file download: $fileSizeDownloaded of $fileSize")
            }
            outputStream.flush()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        } finally {
            inputStream.close()
            if (outputStream != null) {
                outputStream.close()
            }
        }

    }

    /**
     * 计算inputStream的md5值
     * 用于保存文件的name
     */
    fun getInputMD5(inputStream: InputStream): String {
        val digest: MessageDigest?
        try {
            val buffer = ByteArray(1024)
            digest = MessageDigest.getInstance("MD5")
            while (true) {
                val len = inputStream.read(buffer, 0, 1024)
                if (len != -1)
                    digest.update(buffer, 0, len)
                else
                    break
            }
            inputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }
        val bigInt = BigInteger(1, digest!!.digest())
        return bigInt.toString(16)
    }

    /**
     * InputStream无法重复使用
     */
    fun getByteArrayOutputStream(i: InputStream): ByteArrayOutputStream {
        val baos = ByteArrayOutputStream()
        val buffer = ByteArray(1024)
        while (true) {
            val len = i.read(buffer, 0, 1024)
            if (len != -1)
                baos.write(buffer, 0, len)
            else
                break
        }
        baos.flush()
        return baos
    }

}