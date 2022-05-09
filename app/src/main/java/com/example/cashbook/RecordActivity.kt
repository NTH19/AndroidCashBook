package com.example.cashbook

import android.os.Bundle

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager

import com.example.cashbook.adapter.RecordPagerAdapter
import com.example.cashbook.frag_record.IncomeFragment
import com.example.cashbook.frag_record.OutcomeFragment
import com.google.android.material.tabs.TabLayout


class RecordActivity : AppCompatActivity() {
    lateinit var tabLayout:TabLayout
    lateinit var viewPager: ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)
        this.tabLayout = findViewById(R.id.record_tabs)
        this.viewPager = findViewById(R.id.record_vp)
        initPager();

    }
    private fun initPager() {

        val fragmentList: MutableList<Fragment> = ArrayList()

        val outFrag = OutcomeFragment()//支出
        val inFrag = IncomeFragment() //收入
        fragmentList.add(outFrag)
        fragmentList.add(inFrag)

//        创建适配器
        val pagerAdapter = RecordPagerAdapter(supportFragmentManager, fragmentList)
        //        设置适配器
        viewPager.setAdapter(pagerAdapter )
        //将TabLayout和ViwePager进行关联
        tabLayout.setupWithViewPager(viewPager)
    }


    fun onClick(view: View) {
        when (view.id) {
            R.id.record_iv_back->finish()
        }
    }
}