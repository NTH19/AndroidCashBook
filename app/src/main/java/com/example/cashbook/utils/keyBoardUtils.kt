package com.example.cashbook.utils

import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener
import android.text.InputType
import android.view.View
import android.widget.EditText
import com.example.cashbook.R


class KeyBoardUtils(private val keyboardView: KeyboardView, private val editText: EditText) {
    private val k1 //自定义键盘
            : Keyboard

    interface OnEnsureListener {
        fun onEnsure()
    }

    var onEnsureListener: OnEnsureListener? = null
    fun SetOnEnsureListener(onEnsureListener: OnEnsureListener?) {
        this.onEnsureListener = onEnsureListener
    }


    var listener: OnKeyboardActionListener = object : OnKeyboardActionListener {
        override fun onPress(primaryCode: Int) {}
        override fun onRelease(primaryCode: Int) {}
        override fun onKey(primaryCode: Int, keyCodes: IntArray) {
            val editable = editText.text
            val start = editText.selectionStart
            when (primaryCode) {
                Keyboard.KEYCODE_DELETE -> if (editable != null && editable.length > 0) {
                    if (start > 0) {
                        editable.delete(start - 1, start)
                    }
                }
                Keyboard.KEYCODE_CANCEL -> editable!!.clear()
                Keyboard.KEYCODE_DONE -> onEnsureListener!!.onEnsure() //通过接口回调的方法，当点击确定时，可以调用这个方法
                else -> editable!!.insert(start, Character.toString(primaryCode.toChar()))
            }
        }

        override fun onText(text: CharSequence) {}
        override fun swipeLeft() {}
        override fun swipeRight() {}
        override fun swipeDown() {}
        override fun swipeUp() {}
    }

    //    显示键盘
    fun showKeyboard() {
        val visibility = keyboardView.visibility
        if (visibility == View.INVISIBLE || visibility == View.GONE) {
            keyboardView.visibility = View.VISIBLE
        }
    }

    //    隐藏键盘
    fun hideKeyboard() {
        val visibility = keyboardView.visibility
        if (visibility == View.VISIBLE || visibility == View.INVISIBLE) {
            keyboardView.visibility = View.GONE
        }
    }

    init {
        editText.inputType = InputType.TYPE_NULL //取消弹出系统键盘
        k1 = Keyboard(editText.context, R.xml.key)
        keyboardView.keyboard = k1 //设置要显示键盘的样式
        keyboardView.isEnabled = true
        keyboardView.isPreviewEnabled = false
        keyboardView.setOnKeyboardActionListener(listener) //设置键盘按钮被点击了的监听
    }
}