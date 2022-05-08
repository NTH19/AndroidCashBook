package com.example.cashbook.frag_record

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.cashbook.R
import com.example.cashbook.db.TypeBean

class TypeBaseAdapter (var context: Context,var list: MutableList<TypeBean>,var selPos:Int=0): BaseAdapter() {

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(p0: Int): Any {
        return list[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0 as Long
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var p =LayoutInflater.from(context).inflate(R.layout.item_recordfrag_gv,p2,false)
        var imageView=p.findViewById<ImageView>(R.id.item_recordfrag_iv)
        var textView=p.findViewById<TextView>(R.id.item_recordfrag_tv)

        var typeBean=list.get(p0)
        textView.setText(typeBean.typename)
        if(selPos==p0){
            imageView.setImageResource(typeBean.sImageId)
        }else{
            imageView.setImageResource(typeBean.imageId)
        }

        return p
    }

}