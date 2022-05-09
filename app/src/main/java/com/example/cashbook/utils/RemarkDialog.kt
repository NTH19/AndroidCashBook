package com.example.cashbook.utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import com.example.cashbook.R


class RemarkDialog(context: Context) : Dialog(context), View.OnClickListener {
    lateinit var et:EditText
    lateinit var ensureBtn:Button
    lateinit var cancelBtn:Button
    lateinit var ensureListener: onEnsureListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_remark)
        et=findViewById(R.id.dialog_beizhu_et)
        ensureBtn=findViewById(R.id.dialog_beizhu_btn_ensure)
        cancelBtn=findViewById(R.id.dialog_beizhu_btn_cancel)
        ensureBtn.setOnClickListener(this)
        cancelBtn.setOnClickListener(this)
    }
    public interface onEnsureListener{
        public fun ensure()
    }
    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.dialog_beizhu_btn_ensure->{
                ensureListener.ensure()
            }
            R.id.dialog_beizhu_btn_cancel->{
                cancel()
            }
        }
    }
    fun setDialogSize() {
        val window=getWindow()
        val wlp: WindowManager.LayoutParams = window!!.getAttributes()
        val d = window.getWindowManager().getDefaultDisplay()
        wlp.width = d.getWidth()
        wlp.gravity = Gravity.BOTTOM
        window.setBackgroundDrawableResource(android.R.color.transparent)
        window.setAttributes(wlp)
    }

}