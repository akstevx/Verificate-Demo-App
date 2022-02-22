package com.verificate.verificate.framework.presentation.general

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.verificate.verificate.R
import com.verificate.verificate.model.response.states.States
import com.verificate.verificate.util.afterTextChanged
import com.verificate.verificate.util.hide
import com.verificate.verificate.util.show
import com.verificate.verificate.util.updateRecycler
import kotlinx.android.synthetic.main.fragment_state_bottomsheet.*
import kotlinx.android.synthetic.main.fragment_state_bottomsheet.etSearchField

class StateBottomsheetFragment : BottomSheetDialogFragment() {
    private var readStateListener: OnReadStateListener? = null
    val list = States.listOfStates

    interface OnReadStateListener{
            fun onStatePicked(state: String)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_state_bottomsheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            readStateListener = parentFragment as OnReadStateListener
        }
        catch (ex: Exception){ }
        setUpRecycler()
        setUpSearch()
    }

    private fun setUpRecycler() {
        spaceView.hide()
        recyclerState.updateRecycler(requireContext(), list, R.layout.item_selectgender,
            listOf(R.id.textViewagent),
            { innerViews, position ->
                val state = innerViews[R.id.textViewagent] as TextView
                state.text = list[position]
            },
            { position ->
                val item = list[position]
                readStateListener?.onStatePicked(item)
                dialog?.dismiss()
            }
        )
    }

    private fun setUpRecycler(filtered: List<String>) {
        recyclerState.updateRecycler(requireContext(), filtered, R.layout.item_selectgender,
            listOf(R.id.textViewagent),
            { innerViews, position ->
                val state = innerViews[R.id.textViewagent] as TextView
                state.text = filtered[position]
            },
            { position ->
                val item = filtered[position]
                readStateListener?.onStatePicked(item)
                dialog?.dismiss()
            }
        )
    }

    private fun setUpSearch() {
        etSearchField.afterTextChanged { searchText ->
            if(searchText.isEmpty()){
                setUpRecycler()
            }else{
                val filteredList = list.filter { it.lowercase().contains(searchText.lowercase())  ?:false}
                setUpRecycler(filteredList)
                spaceView.show()
            }
        }
    }

}