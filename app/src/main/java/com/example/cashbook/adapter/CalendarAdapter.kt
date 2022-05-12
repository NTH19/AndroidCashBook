package com.example.cashbook.adapter


import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.cashbook.R

class CalendarAdapter(var context: Context, var year: Int) :
    BaseAdapter() {
    var mDatas: MutableList<String>
    var selPos = -1
    @JvmName("setYear1")
    fun setYear(year: Int) {
        this.year = year
        mDatas.clear()
        loadDatas(year)
        notifyDataSetChanged()
    }

    private fun loadDatas(year: Int) {
        for (i in 1..12) {
            val data = "$year/$i"
            mDatas.add(data)
        }
    }

    override fun getCount(): Int {
        return mDatas.size
    }

    override fun getItem(position: Int): Any {
        return mDatas[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var convertView = convertView
        convertView =
            LayoutInflater.from(context).inflate(R.layout.item_dialogcal_gv, parent, false)
        val tv = convertView.findViewById<TextView>(R.id.item_dialogcal_gv_tv)
        tv.text = mDatas[position]
        tv.setBackgroundResource(R.color.grey)
        tv.setTextColor(Color.BLACK)
        if (position == selPos) {
            tv.setBackgroundResource(R.color.green)
            tv.setTextColor(Color.WHITE)
        }
        return convertView
    }

    init {
        mDatas = ArrayList()
        loadDatas(year)
    }
}
