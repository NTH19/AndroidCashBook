package com.example.cashbook


import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cashbook.db.DBManager


class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.setting_iv_back -> finish()
            R.id.setting_tv_clear -> showDeleteDialog()
        }
    }

    private fun showDeleteDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("删除提示")
            .setMessage("您确定要删除所有记录么？\n注意：删除后无法恢复，请慎重选择！")
            .setPositiveButton("取消", null)
            .setNegativeButton("确定") { dialog, which ->
                DBManager.deleteAllAccount()
                Toast.makeText(this@SettingActivity, "删除成功！", Toast.LENGTH_SHORT).show()
            }
        builder.create().show()
    }
}
