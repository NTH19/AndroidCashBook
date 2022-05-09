package com.example.cashbook.frag_record

import android.content.Context
import android.inputmethodservice.KeyboardView
import android.os.Bundle
import android.text.TextUtils
import android.util.LogPrinter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.cashbook.R
import com.example.cashbook.db.AccountBean
import com.example.cashbook.db.DBManager
import com.example.cashbook.db.TypeBean
import com.example.cashbook.utils.KeyBoardUtils
import com.example.cashbook.utils.RemarkDialog
import java.lang.Error
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.collections.ArrayList



open abstract class Base_app_Fragment : androidx.fragment.app.Fragment(),View.OnClickListener {

    lateinit var keyboardView: KeyboardView
    lateinit var moneyEdit: EditText
    lateinit var typeIv:ImageView
    lateinit var timeTv: TextView
    lateinit var remarks: TextView
    lateinit var typeTv: TextView
    lateinit var typeGv: GridView
    lateinit var typeList:MutableList<TypeBean>
    lateinit var adapter:TypeBaseAdapter
    lateinit var accountBean: AccountBean

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.accountBean=AccountBean(0,"其他",R.mipmap.ic_qita,"",0.0,
            "",0,0,0,0)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view=inflater.inflate(R.layout.fragment_outcome, container, false)
        initView(view)
        loadDataToGV()
        setInitlizeTime()
        typeGv.setOnItemClickListener(object :AdapterView.OnItemClickListener{
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                adapter.selPos=p2
                adapter.notifyDataSetInvalidated()
                var bean=typeList.get(p2)
                var name=bean.typename
                accountBean.typename=name
                typeTv.setText(name)
                var sid=bean.sImageId
                typeIv.setImageResource(sid)
                accountBean.slimg=sid
            }
        })
        return view
    }
    private fun setInitlizeTime(){
        var date=Date()
        var partten=SimpleDateFormat("yyyy年MM月dd日 HH:mm")
        var time=partten.format(date)
        timeTv.setText(time)
        accountBean.time=time

        var calendar=Calendar.getInstance()
        accountBean.year=calendar.get(Calendar.YEAR)
        accountBean.month=calendar.get(Calendar.MONTH)+1
        accountBean.day=calendar.get(Calendar.DAY_OF_MONTH)

    }
    open fun loadDataToGV() {
        typeList= ArrayList()
        adapter= TypeBaseAdapter(requireContext(),typeList)
        typeGv.adapter=adapter

    }

    private fun initView( view: View) {
        keyboardView=view.findViewById(R.id.frag_record_keyboard)
        moneyEdit=view.findViewById(R.id.frag_record_et_money)
        typeIv=view.findViewById(R.id.frag_record_iv)
        typeGv=view.findViewById(R.id.frag_record_gv)
        remarks=view.findViewById(R.id.frag_record_tv_beizhu)
        timeTv=view.findViewById(R.id.frag_record_tv_tiime)
        typeTv=view.findViewById(R.id.frag_record_tv)

        timeTv.setOnClickListener(this)
        remarks.setOnClickListener(this)

        var boardUtils=KeyBoardUtils(keyboardView,moneyEdit)
        boardUtils.showKeyboard()

        boardUtils.SetOnEnsureListener(object :KeyBoardUtils.OnEnsureListener{
            override fun onEnsure() {
                var monoeyStr=moneyEdit.text.toString()
                if(TextUtils.isEmpty(monoeyStr)||monoeyStr.equals("0")){
                    activity?.finish()
                    return
                }
                accountBean.price=monoeyStr.toDouble()

                saveAccountToDb()
                activity?.finish()
            }
        })

    }

     open abstract fun saveAccountToDb()
     fun ShowRemarkDialog(){
         var remarkDialog= context?.let { RemarkDialog(it) }
         remarkDialog?.show()
         remarkDialog?.setDialogSize()
         remarkDialog?.ensureListener=object : RemarkDialog.onEnsureListener{
             override fun ensure() {
                 var msg= remarkDialog?.et?.text.toString().trim()
                 if(!TextUtils.isEmpty(msg)){
                     remarks.setText(msg)
                     accountBean.remark=msg
                 }
                 if (remarkDialog != null) {
                     remarkDialog.cancel()
                 }
             }
         }

     }

     override fun onClick(v:View){
        when(v.id){
            R.id.frag_record_tv_tiime->{

            }
            R.id.frag_record_tv_beizhu->{
                ShowRemarkDialog()
            }
        }
     }
}