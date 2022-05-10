package com.example.cashbook.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log


object DBManager {
    private var db: SQLiteDatabase? = null

    fun initDB(context: Context?) {
        val helper = DBOpen(context)
        db = helper.getWritableDatabase()
    }

    fun insertItemToAccounttb(bean: AccountBean) {
        val values = ContentValues()
        values.put("typename", bean.typename)
        values.put("sImageId", bean.slimg)
        values.put("beizhu", bean.remark)
        values.put("money", bean.price)
        values.put("time", bean.time)
        values.put("year", bean.year)
        values.put("month", bean.month)
        values.put("day", bean.day)
        values.put("kind", bean.kind)
        db?.insert("accounttb", null, values)
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
    @SuppressLint("Range")
    fun getAccountListOneDayFromAccounttb(year: Int, month: Int, day: Int): MutableList<AccountBean>? {
        val list: MutableList<AccountBean> = ArrayList()
        val sql = "select * from accounttb where year=? and month=? and day=? order by id desc"
        val cursor = db!!.rawQuery(
            sql,
            arrayOf(year.toString() + "", month.toString() + "", day.toString() + "")
        )
        while (cursor.moveToNext()) {
            val id: Int = cursor.getInt(cursor.getColumnIndex("id"))
            val typename: String = cursor.getString(cursor.getColumnIndex("typename"))
            val beizhu: String = cursor.getString(cursor.getColumnIndex("beizhu"))
            val time: String = cursor.getString(cursor.getColumnIndex("time"))
            val sImageId: Int = cursor.getInt(cursor.getColumnIndex("sImageId"))
            val kind: Int = cursor.getInt(cursor.getColumnIndex("kind"))
            val money: Float = cursor.getFloat(cursor.getColumnIndex("money"))
            val accountBean = AccountBean(
                id, typename, sImageId, beizhu,
                money.toDouble(), time, year, month, day, kind
            )
            list.add(accountBean)
        }
        cursor.close()
        return list
    }
}