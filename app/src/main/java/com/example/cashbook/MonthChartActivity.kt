package com.example.cashbook


import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.cashbook.adapter.ChartVPAdapter
import com.example.cashbook.db.DBManager.getCountItemOneMonth
import com.example.cashbook.db.DBManager.getSumMoneyOneMonth
import com.example.cashbook.utils.CalendarDialog
import com.example.cashbook.utils.CalendarDialog.OnRefreshListener
import com.example.cashbook.frag_chart.OutcomChartFragment
import com.example.cashbook.frag_chart.IncomChartFragment
import java.util.*


class MonthChartActivity : AppCompatActivity() {
    lateinit var inBtn: Button
    lateinit var outBtn: Button
    lateinit var dateTv: TextView
    lateinit var inTv: TextView
    lateinit var outTv: TextView
    lateinit var chartVp: ViewPager
    var year = 0
    var month = 0
    var selectPos = -1
    var selectMonth = -1
    lateinit var chartFragList: MutableList<Fragment?>
    lateinit var incomChartFragment: IncomChartFragment
    lateinit var outcomChartFragment: OutcomChartFragment
    lateinit var chartVPAdapter: ChartVPAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_month_chart)
        initView()
        initTime()
        initStatistics(year, month)
        initFrag()
        setVPSelectListener()
    }

    private fun setVPSelectListener() {
        chartVp.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                setButtonStyle(position)
            }
        })
    }

    private fun initFrag() {
        chartFragList = ArrayList<Fragment?>()
        incomChartFragment = IncomChartFragment()
        outcomChartFragment = OutcomChartFragment()
        val bundle = Bundle()
        bundle.putInt("year", year)
        bundle.putInt("month", month)
        incomChartFragment.setArguments(bundle)
        outcomChartFragment.setArguments(bundle)
        chartFragList.add(outcomChartFragment)
        chartFragList.add(incomChartFragment)
        chartVPAdapter = ChartVPAdapter(getSupportFragmentManager(), chartFragList)
        chartVp.setAdapter(chartVPAdapter)
    }

    /* 统计某年某月的收支情况数据*/
    private fun initStatistics(year: Int, month: Int) {
        val inMoneyOneMonth = getSumMoneyOneMonth(year, month, 1)
        val outMoneyOneMonth = getSumMoneyOneMonth(year, month, 0)
        val incountItemOneMonth = getCountItemOneMonth(year, month, 1)
        val outcountItemOneMonth = getCountItemOneMonth(year, month, 0)
        dateTv.text = year.toString() + "年" + month + "月账单"
        inTv.text = "共" + incountItemOneMonth + "笔收入, ￥ " + inMoneyOneMonth
        outTv.text = "共" + outcountItemOneMonth + "笔支出, ￥ " + outMoneyOneMonth
    }


    private fun initTime() {
        val calendar = Calendar.getInstance()
        year = calendar[Calendar.YEAR]
        month = calendar[Calendar.MONTH] + 1
    }


    private fun initView() {
        inBtn = findViewById(R.id.chart_btn_in)
        outBtn = findViewById(R.id.chart_btn_out)
        dateTv = findViewById(R.id.chart_tv_date)
        inTv = findViewById(R.id.chart_tv_in)
        outTv = findViewById(R.id.chart_tv_out)
        chartVp = findViewById(R.id.chart_vp)
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.chart_iv_back -> finish()
            R.id.chart_iv_rili -> showCalendarDialog()
            R.id.chart_btn_in -> {
                setButtonStyle(1)
                chartVp.setCurrentItem(1)
            }
            R.id.chart_btn_out -> {
                setButtonStyle(0)
                chartVp.setCurrentItem(0)
            }
        }
    }

    private fun showCalendarDialog() {
        val dialog = CalendarDialog(this, selectPos, selectMonth)
        dialog.show()
        dialog.setDialogSize()
        dialog.setOnRefreshListener(object : OnRefreshListener {
            override fun onRefresh(selPos: Int, year: Int, month: Int) {
                selectPos = selPos
                selectMonth = month
                initStatistics(year, month)
                incomChartFragment.setDate(year, month)
                outcomChartFragment.setDate(year, month)
            }
        })
    }

    private fun setButtonStyle(kind: Int) {
        if (kind == 0) {
            outBtn!!.setBackgroundResource(R.drawable.main_record_btn)
            outBtn!!.setTextColor(Color.WHITE)
            inBtn!!.setBackgroundResource(R.drawable.dialog_btn_bg)
            inBtn!!.setTextColor(Color.BLACK)
        } else if (kind == 1) {
            inBtn!!.setBackgroundResource(R.drawable.main_record_btn)
            inBtn!!.setTextColor(Color.WHITE)
            outBtn!!.setBackgroundResource(R.drawable.dialog_btn_bg)
            outBtn!!.setTextColor(Color.BLACK)
        }
    }
}
