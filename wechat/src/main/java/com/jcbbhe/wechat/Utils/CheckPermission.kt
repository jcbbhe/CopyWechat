package com.jcbbhe.wechat.Utils

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.jcbbhe.wechat.ui.activity.BaseActivity

/**
 * Created by jcbbhe on 17/1/13.
 */
object CheckPermission {
    val Permission = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE, //SD卡写权限
            Manifest.permission.READ_EXTERNAL_STORAGE, //SD卡读权限
            Manifest.permission.CAMERA, //摄像头权限
            Manifest.permission.ACCESS_FINE_LOCATION, //通过GPS获取位置权限
            Manifest.permission.ACCESS_COARSE_LOCATION, //通过WiFi或移动基站获取位置权限
            Manifest.permission.RECORD_AUDIO   //麦克风权限
    )


    fun checkPermission(p: String): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }
        if (ContextCompat.checkSelfPermission(BaseActivity.baseActivity!!, p) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(BaseActivity.baseActivity!!, arrayOf(p), 10001)
            return false
        }
        return true
    }


}