package com.jcbbhe.wechat.ui.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.jcbbhe.wechat.Constant
import com.jcbbhe.wechat.R
import com.jcbbhe.wechat.Utils.CheckPermission
import com.jcbbhe.wechat.Utils.RxBus
import com.jcbbhe.wechat.adapter.ViewPagerAdapter
import com.jcbbhe.wechat.ui.fragment.ChatListFragment
import com.jcbbhe.wechat.ui.fragment.ContactsFragment
import com.jcbbhe.wechat.ui.fragment.Findfragment
import com.jcbbhe.wechat.ui.fragment.MeFragment
import kotterknife.bindView


class MainActivity : BaseActivity() {

    val toolbar: Toolbar by bindView(R.id.main_toolbar)
    val viewPager: ViewPager by bindView(R.id.main_view_pager)
    val fragmentArray = arrayOf(ChatListFragment(), ContactsFragment(), Findfragment(), MeFragment())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        viewPager.adapter = ViewPagerAdapter(supportFragmentManager, fragmentArray)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_settings) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        RxBus.get().post(Constant.SUBSCRIBE_GET_STORAGE_WRITE, "asdfasdfasdf")
    }
}
