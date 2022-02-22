package com.verificate.verificate.framework.presentation.general

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.verificate.verificate.databinding.FragmentStatusBottomsheetBinding
import com.verificate.verificate.util.argument
import com.verificate.verificate.util.argumentNullable
import com.verificate.verificate.util.withArguments
import java.io.Serializable

class WarningBottomsheetFragment : BottomSheetDialogFragment() {
    private var binding: FragmentStatusBottomsheetBinding?= null
    val message:String by argument(MESSAGE)
    val onDismissAction:(()->Unit)?  by argumentNullable(ON_DISMISS_ACTION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentStatusBottomsheetBinding.inflate(inflater, container, false)
        return binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateUI()
    }

    private fun updateUI() {
        binding?.textView13?.text = message
//        binding?.imageView19.setBackgroundResource(R.drawable.success_tick)
        binding?.btnPositive?.setOnClickListener {
            onDismissAction?.let {
                it()
            }
            dialog?.dismiss()
        }

        binding?.btnNegative?.setOnClickListener {
            dialog?.dismiss()
        }
    }


    companion object{
//        val TITLE = "title"
        val MESSAGE = "message"
        val ON_DISMISS_ACTION = "onDismissAction"

        fun newInstance(message:String, onDismissAction:() -> Unit): WarningBottomsheetFragment {
            return WarningBottomsheetFragment().withArguments(
//                TITLE to title,
                MESSAGE to message,
                ON_DISMISS_ACTION to onDismissAction as Serializable
            )
        }
    }

}