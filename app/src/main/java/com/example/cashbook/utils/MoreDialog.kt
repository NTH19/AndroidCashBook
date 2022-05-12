package com.example.cashbook.utils


import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.example.cashbook.AboutActivity
import com.example.cashbook.HistoryActivity
import com.example.cashbook.R
import com.example.cashbook.SettingActivity


class MoreDialog(context: Context) : Dialog(context),
    View.OnClickListener {
    lateinit var aboutBtn: Button
    lateinit var settingBtn: Button
    lateinit var historyBtn: Button
    lateinit var infoBtn: Button
    lateinit var errorIv: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_more)
        aboutBtn = findViewById(R.id.dialog_more_btn_about)
        settingBtn = findViewById(R.id.dialog_more_btn_setting)
        historyBtn = findViewById(R.id.dialog_more_btn_record)
        infoBtn = findViewById(R.id.dialog_more_btn_info)
        errorIv = findViewById(R.id.dialog_more_iv)
        aboutBtn.setOnClickListener(this)
        settingBtn.setOnClickListener(this)
        historyBtn.setOnClickListener(this)
        infoBtn.setOnClickListener(this)
        errorIv.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val intent = Intent()
        when (v.id) {
            R.id.dialog_more_btn_about -> {
                intent.setClass(context, AboutActivity::class.java)
                context.startActivity(intent)
            }
            R.id.dialog_more_btn_setting -> {
                intent.setClass(context, SettingActivity::class.java);
                context.startActivity(intent);
            }
            R.id.dialog_more_btn_record -> {
                intent.setClass(context, HistoryActivity::class.java);
                context.startActivity(intent);
            }
            R.id.dialog_more_btn_info -> {

            }
            R.id.dialog_more_iv -> {
            }
        }
        cancel()
    }


    fun setDialogSize() {

        val window = window

        val wlp = window!!.attributes

        val d = window.windowManager.defaultDisplay
        wlp.width = d.width
        wlp.gravity = Gravity.BOTTOM
        window.setBackgroundDrawableResource(android.R.color.transparent)
        window.attributes = wlp
    }
}
