package com.example.cashbook.utils



import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cashbook.R
import com.example.cashbook.adapter.AccountAdapter
import com.example.cashbook.db.AccountBean
import com.example.cashbook.db.DBManager


class SearchActivity : AppCompatActivity() {
    lateinit var searchLv: ListView
    lateinit var searchEt: EditText
    lateinit var emptyTv: TextView
    lateinit var mDatas : MutableList<AccountBean>
    lateinit var adapter: AccountAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initView()
        mDatas = ArrayList()
        adapter = AccountAdapter(this, mDatas)
        searchLv!!.adapter = adapter
        searchLv!!.emptyView = emptyTv //设置无数局时，显示的控件
    }

    private fun initView() {
        searchEt = findViewById(R.id.search_et)
        searchLv = findViewById(R.id.search_lv)
        emptyTv = findViewById(R.id.search_tv_empty)
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.search_iv_back -> finish()
            R.id.search_iv_sh -> {
                val msg = searchEt!!.text.toString().trim { it <= ' ' }
                if (TextUtils.isEmpty(msg)) {
                    Toast.makeText(this, "输入内容不能为空！", Toast.LENGTH_SHORT).show()
                    return
                }
                val list: List<AccountBean> = DBManager.getAccountListByRemarkFromAccounttb(msg)!!
                mDatas!!.clear()
                mDatas!!.addAll(list)
                adapter!!.notifyDataSetChanged()
            }
        }
    }
}
