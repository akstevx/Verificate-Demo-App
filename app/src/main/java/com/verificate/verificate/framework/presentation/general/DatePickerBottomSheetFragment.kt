package com.verificate.verificate.framework.presentation.general

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.verificate.verificate.R
import com.verificate.verificate.util.*
import kotlinx.android.synthetic.main.fragment_date_picker_bottom_sheet.*

class DatePickerBottomSheetFragment : BottomSheetDialogFragment() {

    private val caller:String by argument(CALLER)
    private val dateFormat:String by argument(DATEFORMAT)
    private val titleText:String by argument(TITLE)
    private val buttonText:String by argument(BUTTON)
    private val minDate:Long? by argumentNullable(MINDATE)
    private val maxDate:Long? by argumentNullable(MAXDATE)

    private lateinit var onDatePickedListener: OnDatePickedListener


    interface OnDatePickedListener{
        fun onDatePicked(caller: String,date: String)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_date_picker_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onDatePickedListener = parentFragment as OnDatePickedListener
        updateUI()

    }

    private fun updateUI() {
        textViewTitle.text = titleText
        buttonContinue.text = buttonText
        minDate?.let { datePicker.minDate = it }
        maxDate?.let { datePicker.maxDate = it }
        buttonContinue.setOnClickListener {
            onDatePickedListener.onDatePicked(caller,datePicker.getDate(dateFormat))
            dialog?.dismiss()
        }
        datePicker.setToTodayDate()


    }


    companion object {
        val CALLER = "caller"
        val DATEFORMAT = "dateformat"
        val MINDATE = "mindate"
        val MAXDATE = "maxdate"
        val TITLE = "title"
        val BUTTON = "button"
        fun newInstance(caller:String,dateFormat:String = "dd-MMM-yyyy" ,titleText:String = "Pick Date",buttonText:String = "Ok"):DatePickerBottomSheetFragment{
            return DatePickerBottomSheetFragment().withArguments(CALLER to caller , DATEFORMAT to dateFormat, TITLE to titleText, BUTTON to buttonText)
        }
        fun newInstance(caller: String,dateFormat: String = "dd-MMM-yyyy",titleText:String = "Pick Date",buttonText:String = "Ok",minDate:Long):DatePickerBottomSheetFragment{
            return DatePickerBottomSheetFragment().withArguments(CALLER to caller , DATEFORMAT to dateFormat, MINDATE to minDate, TITLE to titleText, BUTTON to buttonText)
        }

        fun newInstance(caller: String,dateFormat: String = "dd-MMM-yyyy",titleText:String = "Pick Date",buttonText:String = "Ok",date:Long,dateType:DateType):DatePickerBottomSheetFragment{
            return when(dateType){
                DateType.MAX_DATE -> DatePickerBottomSheetFragment().withArguments(CALLER to caller , DATEFORMAT to dateFormat, MAXDATE to date, TITLE to titleText, BUTTON to buttonText)
                DateType.MIN_DATE -> DatePickerBottomSheetFragment().withArguments(CALLER to caller , DATEFORMAT to dateFormat, MINDATE to date, TITLE to titleText, BUTTON to buttonText)
            }

        }

        fun newInstance(caller: String,dateFormat: String = "dd-MMM-yyyy",titleText:String = "Pick Date",buttonText:String = "Ok",minDate:Long,maxDate:Long):DatePickerBottomSheetFragment{
            return DatePickerBottomSheetFragment().withArguments(CALLER to caller , DATEFORMAT to dateFormat, MINDATE to minDate, MAXDATE to maxDate, TITLE to titleText, BUTTON to buttonText)
        }
    }
}
enum class DateType{
    MAX_DATE,MIN_DATE
}