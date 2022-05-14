package com.example.cashbook.frag_chart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.cashbook.R
import com.example.cashbook.adapter.ChartItemAdapter
import com.example.cashbook.db.ChartItemBean
import com.example.cashbook.db.DBManager
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter


abstract class BaseChartFragment : Fragment() {
    lateinit var chartLv: ListView
    var year = 0
    var month = 0
    lateinit var mDatas: MutableList<ChartItemBean>
    lateinit var itemAdapter: ChartItemAdapter
    lateinit var barChart: BarChart
    lateinit var chartTv: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_income_chart, container, false)
        chartLv = view.findViewById(R.id.frag_chart_lv)
        val bundle = arguments
        year = bundle!!.getInt("year")
        month = bundle.getInt("month")
        mDatas = ArrayList<ChartItemBean>()
        itemAdapter = context?.let { ChartItemAdapter(it, mDatas) }!!
        chartLv.setAdapter(itemAdapter)
        //      添加头布局
        addLVHeaderView()
        return view
    }

    protected fun addLVHeaderView() {

        val headerView: View = layoutInflater.inflate(R.layout.item_chartfrag_top, null)

        chartLv!!.addHeaderView(headerView)

        barChart = headerView.findViewById(R.id.item_chartfrag_chart)
        chartTv = headerView.findViewById(R.id.item_chartfrag_top_tv)

        barChart.getDescription().isEnabled = false

        barChart.setExtraOffsets(20f, 20f, 20f, 20f)
        setAxisData(year, month)
    }

    protected abstract fun setAxisData(year: Int, month: Int)

    protected fun setAxis(year: Int, month: Int) {

        val xAxis = barChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(true)

        xAxis.labelCount = 31
        xAxis.textSize = 12f

        xAxis.setValueFormatter(object :IAxisValueFormatter{
            override fun getFormattedValue(value: Float, axis: AxisBase?): String {
                var v=value.toInt()
                if(v==0) return month.toString()+"-1"
                if(v==14)return month.toString()+"-15"
                if(month==2){
                    if(v===27)return month.toString()+"-28"
                }else if(month==1||month==3||month==5||month==7|| month==8||month==10||month==12){
                    if(v==30)return month.toString()+"-31"
                }else if(month==4||month==6||month==9||month==11)return month.toString()+"-30"
                return ""
            }
        } as ValueFormatter?)
        xAxis.yOffset = 10f
        setYAxis(year, month)
    }

    protected abstract fun setYAxis(year: Int, month: Int)
    open fun setDate(year: Int, month: Int) {
        this.year = year
        this.month = month
        // 清空柱状图当中的数据
        barChart!!.clear()
        barChart!!.invalidate()
        setAxis(year, month)
        setAxisData(year, month)
    }

    fun loadData(year: Int, month: Int, kind: Int) {
        val list= DBManager.getChartListFromAccounttb(year, month, kind)
        mDatas!!.clear()
        if (list != null) {
            mDatas!!.addAll(list)
        }
        itemAdapter.notifyDataSetChanged()
    }
}
