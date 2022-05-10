package com.example.cashbook


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView

import androidx.appcompat.app.AppCompatActivity
import com.example.cashbook.db.AccountBean
import com.example.cashbook.db.DBManager.getAccountListOneDayFromAccounttb
import com.example.cashbook.adapter.AccountAdapter
import java.util.*


class MainActivity : AppCompatActivity() {
    lateinit var todayLv //展示今日收支情况的ListView
            : ListView
    lateinit var mDatas:MutableList<AccountBean>
    var year:Int=0
    var month:Int=0
    var day:Int=0
    var adapter: AccountAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initTime()
        todayLv=findViewById(R.id.main_lv)
        mDatas=ArrayList()
        adapter = AccountAdapter(this, mDatas)
        todayLv.adapter = adapter
    }
    override fun onResume() {
        super.onResume()
        loadDBData()
        //setTopTvShow()
    }
    private fun initTime() {
        val calendar: Calendar = Calendar.getInstance()
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH) + 1
        day = calendar.get(Calendar.DAY_OF_MONTH)
    }
    private fun loadDBData() {
        val list = getAccountListOneDayFromAccounttb(year, month, day)
        mDatas.clear()
        list?.let { mDatas.addAll(it) }
        adapter?.notifyDataSetChanged()
    }

    fun onClick(view: View) {
        when(view.id){
            R.id.main_btn_edit->{
                val it1=Intent()
                it1.setClass(this,RecordActivity::class.java)
                startActivity(it1)
            }
        }
    }
}