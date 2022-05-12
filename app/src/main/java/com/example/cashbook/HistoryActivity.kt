package com.example.cashbook



import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView.OnItemLongClickListener
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cashbook.adapter.AccountAdapter
import com.example.cashbook.db.AccountBean
import com.example.cashbook.db.DBManager
import com.example.cashbook.db.DBManager.deleteItemFromAccounttbById
import com.example.cashbook.utils.CalendarDialog

import java.util.*


class HistoryActivity : AppCompatActivity() {
    lateinit var historyLv: ListView
    lateinit var timeTv: TextView
    lateinit var mDatas: MutableList<AccountBean>
    lateinit var adapter: AccountAdapter
    var year = 0
    var month = 0
    var dialogSelPos = -1
    var dialogSelMonth = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        historyLv = findViewById(R.id.history_lv)
        timeTv = findViewById(R.id.history_tv_time)
        mDatas = ArrayList()
        // 设置适配器
        adapter = AccountAdapter(this, mDatas)
        historyLv.setAdapter(adapter)
        initTime()
        timeTv.setText(year.toString() + "年" + month + "月")
        loadData(year, month)
        setLVClickListener()
    }

    /*设置ListView每一个item的长按事件*/
    private fun setLVClickListener() {
        historyLv!!.onItemLongClickListener =
            OnItemLongClickListener { parent, view, position, id ->
                val accountBean = mDatas!![position]
                deleteItem(accountBean)
                false
            }
    }

    private fun deleteItem(accountBean: AccountBean) {
        val delId = accountBean.id
        val builder = AlertDialog.Builder(this)
        builder.setTitle("提示信息").setMessage("您确定要删除这条记录么？")
            .setNegativeButton("取消", null)
            .setPositiveButton("确定") { dialog, which ->
                deleteItemFromAccounttbById(delId)
                mDatas.remove(accountBean)
                adapter.notifyDataSetChanged()
            }
        builder.create().show()
    }


    private fun loadData(year: Int, month: Int) {
        var list = DBManager.getAccountListOneMonthFromAccounttb(year, month)
        mDatas.clear()
        list?.let { mDatas.addAll(it) }
        adapter.notifyDataSetChanged()
    }

    private fun initTime() {
        val calendar = Calendar.getInstance()
        year = calendar[Calendar.YEAR]
        month = calendar[Calendar.MONTH] + 1
    }
    fun onClick(view: View) {
        when (view.id) {
            R.id.history_iv_back -> finish()
            R.id.history_iv_rili -> {
                val dialog = CalendarDialog(this, dialogSelPos, dialogSelMonth)
                dialog.show()
                dialog.setDialogSize()
                dialog.setOnRefreshListener(object : CalendarDialog.OnRefreshListener {
                    override fun onRefresh(selPos: Int, year: Int, month: Int) {
                        timeTv!!.text = year.toString() + "年" + month + "月"
                        loadData(year, month)
                        dialogSelPos = selPos
                        dialogSelMonth = month
                    }
                })
            }
        }
    }
}
