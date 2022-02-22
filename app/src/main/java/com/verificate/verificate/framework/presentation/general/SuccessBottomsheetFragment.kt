package com.verificate.verificate.framework.presentation.general

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.verificate.verificate.databinding.FragmentSuccessBottomsheetBinding
import com.verificate.verificate.util.argument
import com.verificate.verificate.util.argumentNullable
import com.verificate.verificate.util.withArguments
import java.io.Serializable

class SuccessBottomsheetFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentSuccessBottomsheetBinding?= null
    private val binding get() = _binding!!
    private val title:String by argument(TITLE)
    private val body:String by argument(BODY)
    private val onDismissAction:(()->Unit)?  by argumentNullable(ON_DISMISS_ACTION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSuccessBottomsheetBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvSuccessMsg.text = body
        binding.btnSuccessAction.setOnClickListener {
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

        fun newInstance(title:String="", body:String): SuccessBottomsheetFragment {
            return SuccessBottomsheetFragment().withArguments(
                TITLE to title,
                BODY to body)
        }

        fun newInstance(title:String="", body:String, onDismissAction:() -> Unit): SuccessBottomsheetFragment {
            return SuccessBottomsheetFragment().withArguments(
                TITLE to title,
                BODY to body,
                ON_DISMISS_ACTION to onDismissAction as Serializable
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}