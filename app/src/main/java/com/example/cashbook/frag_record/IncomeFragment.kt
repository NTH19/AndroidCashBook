package com.example.cashbook.frag_record

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cashbook.R
import com.example.cashbook.db.DBManager


class IncomeFragment :Base_app_Fragment() {
    public override fun loadDataToGV(){
        super.loadDataToGV()
        var inList= DBManager.getTypeList(1)
        typeList.addAll(inList)
        adapter?.notifyDataSetChanged()
        typeTv.setText("其他")
        typeIv.setImageResource(R.mipmap.in_qt)
    }

    override fun saveAccountToDb() {
        accountBean.kind=1
        DBManager.insertItemToAccounttb(accountBean)
    }
}