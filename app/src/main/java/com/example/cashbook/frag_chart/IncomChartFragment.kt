package com.example.cashbook.frag_chart

import android.graphics.Color
import android.view.View
import com.example.cashbook.db.BarChartItemBean
import com.example.cashbook.db.DBManager
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.ViewPortHandler


class IncomChartFragment : BaseChartFragment() {
    var kind = 1
    override fun onResume() {
        super.onResume()
        loadData(year, month, kind)
    }

    override fun setAxisData(year: Int, month: Int) {
        val sets: MutableList<IBarDataSet> = ArrayList()

        val list = DBManager.getSumMoneyOneDayInMonth(year, month, kind)
        if (list!!.size == 0) {
            barChart.setVisibility(View.GONE)
            chartTv.setVisibility(View.VISIBLE)
        } else {
            barChart.setVisibility(View.VISIBLE)
            chartTv.setVisibility(View.GONE)

            val barEntries1: MutableList<BarEntry> = ArrayList()
            for (i in 0..30) {

                val entry = BarEntry(i.toFloat(), 0.0f)
                barEntries1.add(entry)
            }
            for (i in list.indices) {
                val itemBean: BarChartItemBean = list[i]
                val day: Int = itemBean.day
                val xIndex = day - 1
                val barEntry = barEntries1[xIndex]
                barEntry.y = itemBean.summoney
            }
            val barDataSet1 = BarDataSet(barEntries1, "")
            barDataSet1.valueTextColor = Color.BLACK
            barDataSet1.valueTextSize = 8f
            barDataSet1.color = Color.parseColor("#006400")


            barDataSet1.setValueFormatter(object :IValueFormatter {
                override fun getFormattedValue(
                    value: Float,
                    entry: Entry?,
                    dataSetIndex: Int,
                    viewPortHandler: ViewPortHandler?
                ): String {
                    if(value==0f)return ""
                    return value.toString()
                }
            } as ValueFormatter)
            sets.add(barDataSet1)
            val barData = BarData(sets)
            barData.barWidth = 0.2f
            barChart.setData(barData)
        }
    }

    override fun setYAxis(year: Int, month: Int) {

        val maxMoney: Float = DBManager.getMaxMoneyOneDayInMonth(year, month, kind)
        val max = Math.ceil(maxMoney.toDouble()).toFloat()

        val yAxis_right: YAxis = barChart.getAxisRight()
        yAxis_right.axisMaximum = max
        yAxis_right.axisMinimum = 0f
        yAxis_right.isEnabled = false
        val yAxis_left: YAxis = barChart.getAxisLeft()
        yAxis_left.axisMaximum = max
        yAxis_left.axisMinimum = 0f
        yAxis_left.isEnabled = false


        val legend: Legend = barChart.getLegend()
        legend.isEnabled = false
    }

    override fun setDate(year: Int, month: Int) {
        super.setDate(year, month)
        loadData(year, month, kind)
    }
}