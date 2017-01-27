package com.jcbbhe.wechat.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by jcbbhe on 17/1/1.
 */
class ViewPagerAdapter(fm: FragmentManager, fa: Array<Fragment>) : FragmentPagerAdapter(fm) {
    val fragmentArray = fa

    override fun getCount(): Int {
        return fragmentArray.count()
    }

    override fun getItem(position: Int): Fragment {
        return fragmentArray[position]
    }

}