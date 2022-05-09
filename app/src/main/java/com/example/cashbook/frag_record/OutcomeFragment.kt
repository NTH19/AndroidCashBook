package com.example.cashbook.frag_record

import com.example.cashbook.R
import com.example.cashbook.db.DBManager

class OutcomeFragment: Base_app_Fragment() {

    public override fun loadDataToGV(){
        super.loadDataToGV()
        var outlist= DBManager.getTypeList(0)
        typeList.addAll(outlist)
        adapter?.notifyDataSetChanged()
        typeTv.setText("其他")
        typeIv.setImageResource(R.mipmap.ic_qita)
    }

    override fun saveAccountToDb() {
        accountBean.kind=0
        DBManager.insertItemToAccounttb(accountBean)
    }
}

