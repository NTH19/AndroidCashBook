package com.example.cashbook.utils
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.cashbook.R

class BudgetDialog(context: Context) : Dialog(context),
    View.OnClickListener {
    lateinit var cancelIv: ImageView
    lateinit var ensureBtn: Button
    var moneyEt: EditText? = null

    interface OnEnsureListener {
        fun onEnsure(money: Float)
    }

    lateinit var onEnsureListener: OnEnsureListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_budget)
        cancelIv = findViewById(R.id.dialog_budget_iv_error)
        ensureBtn = findViewById(R.id.dialog_budget_btn_ensure)
        moneyEt = findViewById(R.id.dialog_budget_et)
        cancelIv.setOnClickListener(this)
        ensureBtn.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.dialog_budget_iv_error -> cancel()
            R.id.dialog_budget_btn_ensure -> {
                val data = moneyEt!!.text.toString()
                if (TextUtils.isEmpty(data)) {
                    Toast.makeText(context, "输入数据不能为空！", Toast.LENGTH_SHORT).show()
                    return
                }
                val money = data.toFloat()
                if (money <= 0) {
                    Toast.makeText(context, "预算金额必须大于0", Toast.LENGTH_SHORT).show()
                    return
                }
                if (onEnsureListener != null) {
                    onEnsureListener!!.onEnsure(money)
                }
                cancel()
            }
        }
    }

    fun setDialogSize() {
        val window = window
        val wlp = window!!.attributes
        val d = window.windowManager.defaultDisplay
        wlp.width = d.width //对话框窗口为屏幕窗口
        wlp.gravity = Gravity.BOTTOM
        window.setBackgroundDrawableResource(android.R.color.transparent)
        window.attributes = wlp
        handler.sendEmptyMessageDelayed(1, 100)
    }

    var handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            val inputMethodManager =
                getContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }
}