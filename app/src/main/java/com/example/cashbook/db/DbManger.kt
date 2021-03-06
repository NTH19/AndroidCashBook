package com.example.cashbook.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.cashbook.utils.FloatUtils.div


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
    fun getSumMoneyOneMonth(year: Int, month: Int, kind: Int): Float {
        var total = 0.0f
        val sql = "select sum(money) from accounttb where year=? and month=? and kind=?"
        val cursor = db!!.rawQuery(
            sql,
            arrayOf(year.toString() + "", month.toString() + "", kind.toString() + "")
        )
        // ??????
        if (cursor.moveToFirst()) {
            val money = cursor.getFloat(cursor.getColumnIndex("sum(money)"))
            total = money
        }
        return total
    }

    @SuppressLint("Range")
    fun getCountItemOneMonth(year: Int, month: Int, kind: Int): Int {
        var total = 0
        val sql = "select count(money) from accounttb where year=? and month=? and kind=?"
        val cursor = db!!.rawQuery(
            sql,
            arrayOf(year.toString() + "", month.toString() + "", kind.toString() + "")
        )
        if (cursor.moveToFirst()) {
            val count = cursor.getInt(cursor.getColumnIndex("count(money)"))
            total = count
        }
        return total
    }


    @SuppressLint("Range")
    fun getSumMoneyOneYear(year: Int, kind: Int): Float {
        var total = 0.0f
        val sql = "select sum(money) from accounttb where year=? and kind=?"
        val cursor = db!!.rawQuery(sql, arrayOf(year.toString() + "", kind.toString() + ""))
        // ??????
        if (cursor.moveToFirst()) {
            val money = cursor.getFloat(cursor.getColumnIndex("sum(money)"))
            total = money
        }
        return total
    }
    @SuppressLint("Range")
    fun getSumMoneyOneDay(year: Int, month: Int, day: Int, kind: Int): Float {
        var total = 0.0f
        val sql = "select sum(money) from accounttb where year=? and month=? and day=? and kind=?"
        val cursor: Cursor = db!!.rawQuery(
            sql,
            arrayOf(
                year.toString() + "",
                month.toString() + "",
                day.toString() + "",
                kind.toString() + ""
            )
        )
        if (cursor.moveToFirst()) {
            val money: Float = cursor.getFloat(cursor.getColumnIndex("sum(money)"))
            total = money
        }
        return total
    }
    fun deleteItemFromAccounttbById(id: Int): Int {
        return db!!.delete("accounttb", "id=?", arrayOf(id.toString() + ""))
    }
    @SuppressLint("Range")
    fun getAccountListByRemarkFromAccounttb(beizhu: String): List<AccountBean>? {
        val list: MutableList<AccountBean> = ArrayList()
        val sql = "select * from accounttb where beizhu like '%$beizhu%'"
        val cursor = db!!.rawQuery(sql, null)
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex("id"))
            val typename = cursor.getString(cursor.getColumnIndex("typename"))
            val bz = cursor.getString(cursor.getColumnIndex("beizhu"))
            val time = cursor.getString(cursor.getColumnIndex("time"))
            val sImageId = cursor.getInt(cursor.getColumnIndex("sImageId"))
            val kind = cursor.getInt(cursor.getColumnIndex("kind"))
            val money = cursor.getFloat(cursor.getColumnIndex("money"))
            val year = cursor.getInt(cursor.getColumnIndex("year"))
            val month = cursor.getInt(cursor.getColumnIndex("month"))
            val day = cursor.getInt(cursor.getColumnIndex("day"))
            val accountBean = AccountBean(
                id, typename, sImageId, bz,
                money.toDouble(), time, year, month, day, kind
            )
            list.add(accountBean)
        }
        return list
    }

    @SuppressLint("Range")
    fun getAccountListOneMonthFromAccounttb(year: Int, month: Int): List<AccountBean>? {
        val list: MutableList<AccountBean> = ArrayList()
        val sql = "select * from accounttb where year=? and month=? order by id desc"
        val cursor = db!!.rawQuery(sql, arrayOf(year.toString() + "", month.toString() + ""))
        //????????????????????????????????????
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex("id"))
            val typename = cursor.getString(cursor.getColumnIndex("typename"))
            val beizhu = cursor.getString(cursor.getColumnIndex("beizhu"))
            val time = cursor.getString(cursor.getColumnIndex("time"))
            val sImageId = cursor.getInt(cursor.getColumnIndex("sImageId"))
            val kind = cursor.getInt(cursor.getColumnIndex("kind"))
            val money = cursor.getFloat(cursor.getColumnIndex("money"))
            val day = cursor.getInt(cursor.getColumnIndex("day"))
            val accountBean = AccountBean(
                id, typename, sImageId, beizhu,
                money.toDouble(), time, year, month, day, kind
            )
            list.add(accountBean)
        }
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
    fun deleteAllAccount() {
        val sql = "delete from accounttb"
        db!!.execSQL(sql)
    }

    @SuppressLint("Range")
    fun getYearListFromAccounttb(): List<Int>? {
        val list: MutableList<Int> = ArrayList()
        val sql = "select distinct(year) from accounttb order by year asc"
        val cursor = db!!.rawQuery(sql, null)
        while (cursor.moveToNext()) {
            val year = cursor.getInt(cursor.getColumnIndex("year"))
            list.add(year)
        }
        return list
    }
    @SuppressLint("Range")
    fun getChartListFromAccounttb(year: Int, month: Int, kind: Int): List<ChartItemBean>? {
        val list: MutableList<ChartItemBean> = ArrayList()
        val sumMoneyOneMonth = getSumMoneyOneMonth(year, month, kind)
        val sql =
            "select typename,sImageId,sum(money)as total from accounttb where year=? and month=? and kind=? group by typename " +
                    "order by total desc"
        val cursor = db!!.rawQuery(
            sql,
            arrayOf(year.toString() + "", month.toString() + "", kind.toString() + "")
        )
        while (cursor.moveToNext()) {
            val sImageId = cursor.getInt(cursor.getColumnIndex("sImageId"))
            val typename = cursor.getString(cursor.getColumnIndex("typename"))
            val total = cursor.getFloat(cursor.getColumnIndex("total"))
            val ratio = div(total, sumMoneyOneMonth)
            val bean = ChartItemBean(sImageId, typename, ratio, total)
            list.add(bean)
        }
        return list
    }
    @SuppressLint("Range")
    fun getMaxMoneyOneDayInMonth(year: Int, month: Int, kind: Int): Float {
        val sql =
            "select sum(money) from accounttb where year=? and month=? and kind=? group by day order by sum(money) desc"
        val cursor = db!!.rawQuery(
            sql,
            arrayOf(year.toString() + "", month.toString() + "", kind.toString() + "")
        )
        return if (cursor.moveToFirst()) {
            cursor.getFloat(cursor.getColumnIndex("sum(money)"))
        } else 0f
    }
    @SuppressLint("Range")
    fun getSumMoneyOneDayInMonth(year: Int, month: Int, kind: Int): List<BarChartItemBean>? {
        val sql =
            "select day,sum(money) from accounttb where year=? and month=? and kind=? group by day"
        val cursor = db!!.rawQuery(
            sql,
            arrayOf(year.toString() + "", month.toString() + "", kind.toString() + "")
        )
        val list: MutableList<BarChartItemBean> = ArrayList()
        while (cursor.moveToNext()) {
            val day = cursor.getInt(cursor.getColumnIndex("day"))
            val smoney = cursor.getFloat(cursor.getColumnIndex("sum(money)"))
            val itemBean = BarChartItemBean(year, month, day, smoney)
            list.add(itemBean)
        }
        return list
    }
}