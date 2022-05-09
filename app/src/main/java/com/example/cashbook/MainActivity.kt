package com.example.cashbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClick(view: View) {
        when(view.id){
            R.id.main_btn_edit->{
                val it1=Intent()
                it1.setClass(this,RecordActivity::class.java)
                startActivity(it1)
            }
        }
    }
}