package com.example.cashbook.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.GridView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.cashbook.R
import com.example.cashbook.adapter.CalendarAdapter
import com.example.cashbook.db.DBManager

import java.util.*


class CalendarDialog(context: Context, selectPos: Int, selectMonth: Int) :
    Dialog(context), View.OnClickListener {
    lateinit var errorIv: ImageView
    lateinit var gv: GridView
    lateinit var hsvLayout: LinearLayout
    lateinit var hsvViewList: MutableList<TextView>
    lateinit var yearList: MutableList<Int>
    var selectPos = -1
    lateinit var adapter: CalendarAdapter
    var selectMonth = -1

    interface OnRefreshListener {
        fun onRefresh(selPos: Int, year: Int, month: Int)
    }

    lateinit var onrefreshListener: OnRefreshListener
    fun setOnRefreshListener(onRRefreshListener: OnRefreshListener?) {
        if (onRRefreshListener != null) {
            this.onrefreshListener = onRRefreshListener
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_calendar)
        gv = findViewById(R.id.dialog_calendar_gv)
        errorIv = findViewById(R.id.dialog_calendar_iv)
        hsvLayout = findViewById(R.id.dialog_calendar_layout)
        errorIv.setOnClickListener(this)
        addViewToLayout()
        initGridView()
        setGVListener()
    }

    private fun setGVListener() {
        gv!!.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->
                adapter.selPos = position
                adapter.notifyDataSetInvalidated()
                val month = position + 1
                val year: Int = adapter.year
                onrefreshListener!!.onRefresh(selectPos, year, month)
                cancel()
            }
    }

    private fun initGridView() {
        val selYear = yearList!![selectPos]
        adapter = CalendarAdapter(context, selYear)
        if (selectMonth == -1) {
            val month = Calendar.getInstance()[Calendar.MONTH]
            adapter.selPos = month
        } else {
            adapter.selPos = selectMonth - 1
        }
        gv!!.adapter = adapter
    }

    private fun addViewToLayout() {
        hsvViewList = ArrayList()
        yearList = DBManager.getYearListFromAccounttb() as MutableList<Int>
        //????????????????????????????????????????????????????????????
        if (yearList!!.size == 0) {
            val year = Calendar.getInstance()[Calendar.YEAR]
            yearList!!.add(year)
        }

        //?????????????????????????????????ScrollView??????????????????view
        for (i in yearList!!.indices) {
            val year = yearList!![i]
            val view: View = layoutInflater.inflate(R.layout.item_dialogcal_hsv, null)
            hsvLayout!!.addView(view) //???view?????????????????????
            val hsvTv = view.findViewById<TextView>(R.id.item_dialogcal_hsv_tv)
            hsvTv.text = year.toString() + ""
            hsvViewList.add(hsvTv)
        }
        if (selectPos == -1) {
            selectPos = hsvViewList.size - 1 //??????????????????????????????????????????
        }
        changeTvbg(selectPos) //????????????????????????????????????
        setHSVClickListener() //???????????????View???????????????
    }

    private fun setHSVClickListener() {
        for (i in hsvViewList!!.indices) {
            val view = hsvViewList!![i]
            view.setOnClickListener {
                changeTvbg(i)
                selectPos = i
                val year = yearList!![selectPos]
                adapter.setYear(year)
            }
        }
    }

    private fun changeTvbg(selectPos: Int) {
        for (i in hsvViewList!!.indices) {
            val tv = hsvViewList!![i]
            tv.setBackgroundResource(R.drawable.dialog_btn_bg)
            tv.setTextColor(Color.BLACK)
        }
        val selView = hsvViewList!![selectPos]
        selView.setBackgroundResource(R.drawable.main_more_btn_bg)
        selView.setTextColor(Color.WHITE)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.dialog_calendar_iv -> cancel()
        }
    }


    fun setDialogSize() {
        val window = window
        val wlp = window!!.attributes
        //        ??????????????????
        val d = window.windowManager.defaultDisplay
        wlp.width = d.width
        wlp.gravity = Gravity.TOP
        window.setBackgroundDrawableResource(android.R.color.transparent)
        window.attributes = wlp
    }

    init {
        this.selectPos = selectPos
        this.selectMonth = selectMonth
    }
}
