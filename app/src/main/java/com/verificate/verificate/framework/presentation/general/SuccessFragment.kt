package com.verificate.verificate.framework.presentation.general

import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.verificate.verificate.base.BaseFragment
import com.verificate.verificate.databinding.FragmentSuccessBinding
import com.verificate.verificate.util.argument
import com.verificate.verificate.util.withArguments
import java.io.Serializable


class SuccessFragment : DialogFragment() {
    private var binding: FragmentSuccessBinding?= null
    private val title:String by argument(SUCCESS_TITLE)
    private val message:String by argument(SUCCESS_MESSAGE)
    private val action:(()->Unit)  by argument(ON_BUTTON_CLICKED)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // return super.onCreateDialog(savedInstanceState)
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSuccessBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateViews()
    }

    private fun updateViews(){
        setDialog()
        binding?.txtSuccessHeader?.text = title
        binding?.txtSuccessBody?.text = message
        binding?.btnSuccess?.setOnClickListener {
            action()
            dialog?.dismiss()
        }
    }

    private fun setDialog() {
        dialog?.window?.setGravity(Gravity.TOP)
        val dialog: BottomSheetDialog? = dialog as BottomSheetDialog?
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = 650
            dialog.window?.setLayout(width, height)
        }
    }

    companion object{
        val SUCCESS_TITLE = "body"
        val SUCCESS_MESSAGE = "message"
        val ON_BUTTON_CLICKED = "ONBUTTONCLICKED"

        fun newInstance(successTitle:String, successMessage: String, action:() -> Unit): SuccessFragment {
            return SuccessFragment().withArguments(
                SUCCESS_TITLE to successTitle,
                SUCCESS_MESSAGE to successMessage,
                ON_BUTTON_CLICKED to action as Serializable
            )
        }
    }

}