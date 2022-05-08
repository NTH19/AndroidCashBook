package com.example.cashbook.utils

import android.app.Application
import com.example.cashbook.db.DBManager

class UniteApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        DBManager.initDB(applicationContext);
    }
}