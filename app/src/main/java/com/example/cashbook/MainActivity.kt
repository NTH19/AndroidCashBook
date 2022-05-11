package com.example.cashbook


import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.View.OnClickListener
import android.widget.*
import android.widget.AdapterView.OnItemLongClickListener
import androidx.appcompat.app.AppCompatActivity
import com.example.cashbook.adapter.AccountAdapter
import com.example.cashbook.db.AccountBean
import com.example.cashbook.db.DBManager
import com.example.cashbook.db.DBManager.getAccountListOneDayFromAccounttb
import com.example.cashbook.db.DBManager.getSumMoneyOneDay
import com.example.cashbook.db.DBManager.getSumMoneyOneMonth
import com.example.cashbook.utils.BudgetDialog
import java.util.*


class MainActivity : AppCompatActivity() , OnClickListener{
    lateinit var todayLv
            : ListView
    lateinit var mDatas:MutableList<AccountBean>
    var year:Int=0
    var month:Int=0
    var day:Int=0
    var adapter: AccountAdapter? = null
    lateinit var searchIv: ImageView
    lateinit var editBtn: Button
    lateinit var moreBtn: ImageButton
    lateinit var headerView: View
    var topOutTv: TextView? = null
    var topInTv:TextView? = null
    lateinit var topbudgetTv:TextView
    lateinit var topConTv:TextView
    lateinit var topShowIv: ImageView
    lateinit var preferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initTime()
        preferences = getSharedPreferences("budget", Context.MODE_PRIVATE);
        mDatas=ArrayList()
        addLVHeaderView()
        adapter = AccountAdapter(this, mDatas)
        todayLv.adapter = adapter
    }
    private fun initView() {
        todayLv = findViewById(R.id.main_lv)
        editBtn = findViewById(R.id.main_btn_edit)
        moreBtn = findViewById(R.id.main_btn_more)
        searchIv = findViewById(R.id.main_iv_search)
        editBtn.setOnClickListener(this)
        moreBtn.setOnClickListener(this)
        searchIv.setOnClickListener(this)
        setLVLongClickListener()
    }

    private fun setLVLongClickListener() {
        todayLv.onItemLongClickListener =
            OnItemLongClickListener { parent, view, position, id ->
                if (position == 0) {
                    return@OnItemLongClickListener false
                }
                val pos = position - 1
                val clickBean = mDatas[pos]


                showDeleteItemDialog(clickBean)
                false
            }
    }
    private fun showDeleteItemDialog(clickBean: AccountBean) {
        val builder: AlertDialog.Builder = Builder(this)
        builder.setTitle("提示信息").setMessage("您确定要删除这条记录么？")
            .setNegativeButton("取消", null)
            .setPositiveButton("确定", DialogInterface.OnClickListener { dialog, which ->
                val click_id = clickBean.id
                //执行删除的操作
                DBManager.deleteItemFromAccounttbById(click_id)
                mDatas.remove(clickBean)
                adapter!!.notifyDataSetChanged()
                setTopTvShow()
            })
        builder.create().show()
    }
    private fun setTopTvShow() {

        val incomeOneDay = getSumMoneyOneDay(year, month, day, 1)
        val outcomeOneDay = getSumMoneyOneDay(year, month, day, 0)
        val infoOneDay = "今日支出 ￥$outcomeOneDay  收入 ￥$incomeOneDay"
        topConTv.text = infoOneDay

        val incomeOneMonth = getSumMoneyOneMonth(year, month, 1)
        val outcomeOneMonth = getSumMoneyOneMonth(year, month, 0)
        topInTv!!.text = "￥$incomeOneMonth"
        topOutTv!!.text = "￥$outcomeOneMonth"

        val bmoney = preferences.getFloat("bmoney", 0F) //预算
        if (bmoney == 0f) {
            topbudgetTv.text = "￥ 0"
        } else {
            val syMoney = bmoney - outcomeOneMonth
            topbudgetTv.text = "￥$syMoney"
        }
    }


    private fun addLVHeaderView() {
        //将布局转换成View对象
        headerView = layoutInflater.inflate(R.layout.item_mainlv_top, null)
        todayLv.addHeaderView(headerView)
        //查找头布局可用控件
        topOutTv = headerView.findViewById(R.id.item_mainlv_top_tv_out)
        topInTv = headerView.findViewById(R.id.item_mainlv_top_tv_in)
        topbudgetTv = headerView.findViewById(R.id.item_mainlv_top_tv_buget)
        topConTv = headerView.findViewById(R.id.item_mainlv_top_tv_day)
        topShowIv = headerView.findViewById(R.id.item_mainlv_top_iv_hide)
        topbudgetTv.setOnClickListener(this)
        headerView.setOnClickListener(this)
        topShowIv.setOnClickListener(this)
    }
    override fun onResume() {
        super.onResume()
        loadDBData()
        setTopTvShow()
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

    override fun onClick(v: View) {
        when (v.id) {
            R.id.main_btn_edit->{
                val it1=Intent()
                it1.setClass(this,RecordActivity::class.java)
                startActivity(it1)
            }
            R.id.main_iv_search -> {

            }
            R.id.main_btn_more -> {
            }
            R.id.item_mainlv_top_tv_buget-> showBudgetDialog()

            R.id.item_mainlv_top_iv_hide ->toggleShow()
        }
        if (v === headerView) {
            //val intent = Intent()
            //intent.setClass(this, MonthChartActivity::class.java)
           // startActivity(intent)
        }
    }
    var isShow :Boolean= true
    private fun showBudgetDialog() {
        val dialog = BudgetDialog(this)
        dialog.show()
        dialog.setDialogSize()
        dialog.onEnsureListener = object : BudgetDialog.OnEnsureListener {
            override fun onEnsure(money: Float) {
                val editor = preferences.edit()
                editor.putFloat("bmoney", money)
                editor.commit()

                val outcomeOneMonth = getSumMoneyOneMonth(year, month, 0)
                val syMoney = money - outcomeOneMonth
                topbudgetTv.text = "￥$syMoney"
            }
        }
    }
    private fun toggleShow() {
        if (isShow) {
            val passwordMethod = PasswordTransformationMethod.getInstance()
            topInTv!!.transformationMethod = passwordMethod
            topOutTv!!.transformationMethod = passwordMethod
            topbudgetTv.transformationMethod = passwordMethod
            topShowIv.setImageResource(R.mipmap.ih_hide)
            isShow = false
        } else {
            val hideMethod = HideReturnsTransformationMethod.getInstance()
            topInTv!!.transformationMethod = hideMethod
            topOutTv!!.transformationMethod = hideMethod
            topbudgetTv.transformationMethod = hideMethod
            topShowIv.setImageResource(R.mipmap.ih_show)
            isShow = true
        }
    }
}