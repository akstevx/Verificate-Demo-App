package com.verificate.verificate.framework.presentation.general

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.verificate.verificate.R
import com.verificate.verificate.util.argumentNullable
import com.verificate.verificate.util.updateRecycler
import com.verificate.verificate.util.withArguments
import kotlinx.android.synthetic.main.fragment_read_gender_bottom_sheet.*
import java.io.Serializable


class ReadGenderBottomSheetFragment : BottomSheetDialogFragment() {
    private val status: Boolean? by argumentNullable(BIKE_STATUS)

    private var readGenderListener: OnReadGenderListener? = null
    interface OnReadGenderListener{
        fun onGenderPicked(gender: GenderObj)
    }

    private var bikeStatusListener: OnBikeStatusListener? = null
    interface OnBikeStatusListener{
        fun onStatusPicked(status: String)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_read_gender_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            readGenderListener = parentFragment as OnReadGenderListener
            bikeStatusListener = parentFragment as OnBikeStatusListener
        }
        catch (ex: Exception){ }

        status?.let { setUpBikeRecycler() } ?: run{
            setUpGenderRecycler()
        }
    }

    private fun setUpGenderRecycler() {
        val it = listOf<GenderObj>(
                GenderObj("Male"),
                GenderObj("Female")
            )
            recyclerReadGender.updateRecycler(requireContext(), it, R.layout.item_selectgender,
                listOf(R.id.textViewagent),
                { innerViews, position ->
                    val gender = innerViews[R.id.textViewagent] as TextView
                    gender.text = it[position].genderName
                },
                { position ->
                    val item = it[position]
                    readGenderListener?.onGenderPicked(item)
                    dialog?.dismiss()
                }
            )
    }

    private fun setUpBikeRecycler() {
        val it = listOf<String>("Yes", "No")

        recyclerReadGender.updateRecycler(requireContext(), it, R.layout.item_selectgender,
            listOf(R.id.textViewagent),
            { innerViews, position ->
                val gender = innerViews[R.id.textViewagent] as TextView
                gender.text = it[position]
            },
            { position ->
                val item = it[position]
                bikeStatusListener?.onStatusPicked(item)
                dialog?.dismiss()
            }
        )
    }

    data class GenderObj(val genderName: String)

    companion object{
        val BIKE_STATUS = "BIKE_STATUS"
        fun newInstance(isBikeStatus: Boolean): ReadGenderBottomSheetFragment {
            return ReadGenderBottomSheetFragment().withArguments(BIKE_STATUS to isBikeStatus as Serializable)
        }
    }
}

