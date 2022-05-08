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

/**
 * A simple [Fragment] subclass.
 * Use the [OutcomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OutcomeFragment : androidx.fragment.app.Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
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
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view?.let { initView(it) }
        loadDataToGV()
        return inflater.inflate(R.layout.fragment_outcome, container, false)
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OutcomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OutcomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}