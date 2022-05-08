package com.example.cashbook.db

import android.annotation.SuppressLint
import com.example.cashbook.db.DBOpen
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.cashbook.db.TypeBean


object DBManager {
    private var db: SQLiteDatabase? = null

    fun initDB(context: Context?) {
        val helper = DBOpen(context)
        db = helper.getWritableDatabase()
    }

    @SuppressLint("Range")
    fun getTypeList(kind: Int): MutableList<TypeBean> {
        val list: MutableList<TypeBean> = ArrayList()

        val sql = "select * from typetb where kind = $kind"
        val cursor = db!!.rawQuery(sql, null)

        while (cursor.moveToNext()) {
            val typename = cursor.getString(cursor.getColumnIndex("typename"))
            val imageId = cursor.getInt(cursor.getColumnIndex("imageId"))
            val sImageId = cursor.getInt(cursor.getColumnIndex("sImageId"))
            val kind1 = cursor.getInt(cursor.getColumnIndex("kind"))
            val id = cursor.getInt(cursor.getColumnIndex("id"))
            val typeBean = TypeBean(id, typename, imageId, sImageId, kind)
            list.add(typeBean)
        }
        cursor.close()
        return list
    }
}