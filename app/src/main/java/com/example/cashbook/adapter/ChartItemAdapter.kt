package com.example.cashbook.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.cashbook.R
import com.example.cashbook.db.ChartItemBean
import com.example.cashbook.utils.FloatUtils



class ChartItemAdapter(var context: Context, mDatas: List<ChartItemBean>) :
    BaseAdapter() {
    var mDatas: List<ChartItemBean>
    var inflater: LayoutInflater
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
            convertView = inflater.inflate(R.layout.item_chartfrag_lv, parent, false)
            holder = ViewHolder(convertView)
            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }
        val bean: ChartItemBean = mDatas[position]
        holder.iv.setImageResource(bean.sImageId)
        holder.typeTv.setText(bean.type)
        val ratio: Float = bean.ratio
        val pert: String = FloatUtils.ratioToPercent(ratio)
        holder.ratioTv.text = pert
        holder.totalTv.text = "￥ " + bean.totalMoney
        return convertView
    }

    internal inner class ViewHolder(view: View) {
        var typeTv: TextView
        var ratioTv: TextView
        var totalTv: TextView
        var iv: ImageView

        init {
            typeTv = view.findViewById(R.id.item_chartfrag_tv_type)
            ratioTv = view.findViewById(R.id.item_chartfrag_tv_pert)
            totalTv = view.findViewById(R.id.item_chartfrag_tv_sum)
            iv = view.findViewById(R.id.item_chartfrag_iv)
        }
    }

    init {
        this.mDatas = mDatas
        inflater = LayoutInflater.from(context)
    }
}
