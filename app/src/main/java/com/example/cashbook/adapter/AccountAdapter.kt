package com.example.cashbook.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.cashbook.R
import com.example.cashbook.db.AccountBean
import java.util.*

class AccountAdapter(var context: Context, var mDatas: MutableList<AccountBean>) :
    BaseAdapter() {
    var inflater: LayoutInflater
    var year: Int
    var month: Int
    var day: Int
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
        var holder: ViewHolder? = null
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_mainlv, parent, false)
            holder = ViewHolder(convertView)
            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }
        val bean = mDatas[position]
        holder!!.typeIv.setImageResource(bean.slimg)
        holder.typeTv.text = bean.typename
        holder.beizhuTv.setText(bean.remark)
        holder.moneyTv.text = "￥ " + bean.price
        if (bean.year == year && bean.month == month && bean.day == day) {
            val time = bean.time.split(" ").toTypedArray()[1]
            holder.timeTv.text = "今天 $time"
        } else {
            holder.timeTv.text = bean.time
        }
        return convertView
    }

    internal inner class ViewHolder(view: View) {
        var typeIv: ImageView
        var typeTv: TextView
        var beizhuTv: TextView
        var timeTv: TextView
        var moneyTv: TextView

        init {
            typeIv = view.findViewById(R.id.item_mainlv)
            typeTv = view.findViewById(R.id.item_mainlv_v_title)
            timeTv = view.findViewById(R.id.item_mainlv_v_time)
            beizhuTv = view.findViewById(R.id.item_mainlv_v_beizhu)
            moneyTv = view.findViewById(R.id.item_mainlv_v_moneu)
        }
    }

    init {
        inflater = LayoutInflater.from(context)
        val calendar = Calendar.getInstance()
        year = calendar[Calendar.YEAR]
        month = calendar[Calendar.MONTH] + 1
        day = calendar[Calendar.DAY_OF_MONTH]
    }
}