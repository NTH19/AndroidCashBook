package com.example.cashbook.frag_record

import android.content.Context
import android.inputmethodservice.KeyboardView
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import com.example.cashbook.R
import com.example.cashbook.db.DBManager
import com.example.cashbook.db.TypeBean
import com.example.cashbook.utils.KeyBoardUtils

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class OutcomeFragment : androidx.fragment.app.Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var keyboardView: KeyboardView
    lateinit var moneyEdit: EditText
    lateinit var typeIv:ImageView
    lateinit var timeTv: TextView
    lateinit var remarks: TextView
    lateinit var typeTv: TextView
    lateinit var typeGv: GridView
    lateinit var typeList:MutableList<TypeBean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view=inflater.inflate(R.layout.fragment_outcome, container, false)
        initView(view)
       // loadDataToGV()
        return view
    }

    private fun loadDataToGV() {
        typeList= ArrayList()
        var adapter=context?.let { TypeBaseAdapter(it,typeList) }
        typeGv.adapter=adapter
        var outlist=DBManager.getTypeList(0)
        typeList.addAll(outlist)
        adapter?.notifyDataSetChanged()
    }

    private fun initView( view: View) {
        keyboardView=view.findViewById(R.id.frag_record_keyboard)
        moneyEdit=view.findViewById(R.id.frag_record_et_money)
        typeIv=view.findViewById(R.id.frag_record_iv)
        typeGv=view.findViewById(R.id.frag_record_gv)
        remarks=view.findViewById(R.id.frag_record_tv_beizhu)
        timeTv=view.findViewById(R.id.frag_record_tv_tiime)
        typeTv=view.findViewById(R.id.frag_record_tv)
        var boardUtils=KeyBoardUtils(keyboardView,moneyEdit)
        boardUtils.showKeyboard()

        boardUtils.SetOnEnsureListener(object :KeyBoardUtils.OnEnsureListener{
            override fun onEnsure() {
                TODO("Not yet implemented")
            }
        })

    }
}