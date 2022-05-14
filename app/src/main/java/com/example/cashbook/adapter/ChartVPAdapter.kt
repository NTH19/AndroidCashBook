package com.example.cashbook.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


class ChartVPAdapter(fm: FragmentManager, var fragmentList: MutableList<Fragment?>) :
    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return fragmentList[position]!!
    }

    override fun getCount(): Int {
        return fragmentList.size
    }
}
