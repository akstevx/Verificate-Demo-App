package com.verificate.verificate.framework.presentation.general

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.verificate.verificate.R
import com.verificate.verificate.util.*
import kotlinx.android.synthetic.main.fragment_l_g_a_bottomsheet.*
import java.io.Serializable

class LGABottomsheetFragment : BottomSheetDialogFragment() {
    private var readStateListener: OnReadLGAListener? = null
    private val listOfLGA: List<String> by argument(LGA)

    interface OnReadLGAListener{
        fun onLgaPicked(state: String)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_l_g_a_bottomsheet, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            readStateListener = parentFragment as OnReadLGAListener
        }
        catch (ex: Exception){ }
        setUpRecycler()
        setUpSearch()
    }

    private fun setUpRecycler() {
        spaceView2.hide()
        recyclerLGA.updateRecycler(requireContext(), listOfLGA, R.layout.item_selectgender,
            listOf(R.id.textViewagent),
            { innerViews, position ->
                val state = innerViews[R.id.textViewagent] as TextView
                state.text = listOfLGA[position]
            },
            { position ->
                val item = listOfLGA[position]
                readStateListener?.onLgaPicked(item)
                dialog?.dismiss()
            }
        )
    }

    private fun setUpRecycler(filtered: List<String>) {
        recyclerLGA.updateRecycler(requireContext(), filtered, R.layout.item_selectgender,
            listOf(R.id.textViewagent),
            { innerViews, position ->
                val state = innerViews[R.id.textViewagent] as TextView
                state.text = filtered[position]
            },
            { position ->
                val item = filtered[position]
                readStateListener?.onLgaPicked(item)
                dialog?.dismiss()
            }
        )
    }

    private fun setUpSearch() {
        etSearchField.afterTextChanged { searchText ->
            if(searchText.isEmpty()){
                setUpRecycler()
            }else{
                val filteredList = listOfLGA.filter { it.lowercase()?.contains(searchText.lowercase())  ?:false}
                setUpRecycler(filteredList)
                spaceView2.show()
            }
        }
    }

    companion object{
        val LGA = "localGovernments"
        fun newInstance(listOfLGA: List<String>): LGABottomsheetFragment {
            return LGABottomsheetFragment().withArguments(LGA to listOfLGA as Serializable)
        }
    }

}