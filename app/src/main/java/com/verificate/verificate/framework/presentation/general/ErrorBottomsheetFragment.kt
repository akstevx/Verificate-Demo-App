package com.verificate.verificate.framework.presentation.general

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.verificate.verificate.databinding.FragmentErrorBottomsheetBinding
import com.verificate.verificate.util.argument
import com.verificate.verificate.util.argumentNullable
import com.verificate.verificate.util.withArguments
import java.io.Serializable


class ErrorBottomsheetFragment : BottomSheetDialogFragment() {
    private var binding: FragmentErrorBottomsheetBinding?= null
    val title:String by argument(TITLE)
    val body:String by argument(BODY)
    val onDismissAction:(()->Unit)?  by argumentNullable(ON_DISMISS_ACTION)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentErrorBottomsheetBinding.inflate(inflater, container, false)
        return binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setGravity(Gravity.TOP)
        updateUI()
    }

    private fun updateUI() {
        binding?.tvErrorMsg?.text = body
//        binding?.imageView19.setBackgroundResource(R.drawable.success_tick)
        binding?.btnErrorAction?.setOnClickListener {
            onDismissAction?.let {
                it()
            }
           dialog?.dismiss()
        }

    }

    companion object{
        val TITLE = "title"
        val BODY = "body"
        val ON_DISMISS_ACTION = "onDismissAction"

        fun newInstance(title:String, body:String): ErrorBottomsheetFragment {
            return ErrorBottomsheetFragment().withArguments(
                TITLE to title,
                BODY to body)
        }

        fun newInstance(title:String, body:String, onDismissAction:() -> Unit): ErrorBottomsheetFragment {
            return ErrorBottomsheetFragment().withArguments(
                TITLE to title,
                BODY to body,
                ON_DISMISS_ACTION to onDismissAction as Serializable
            )
        }
    }

}
