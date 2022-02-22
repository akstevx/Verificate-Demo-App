package com.verificate.verificate.framework.presentation.fragments.main.dashboard.verification

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.verificate.verificate.databinding.VerificationItemLayoutBinding
import com.verificate.verificate.model.response.verification.Verification
import com.verificate.verificate.util.GenericViewHolder
import com.verificate.verificate.util.SingleLiveEvent
import com.verificate.verificate.util.capitalizeWords


class VerificationAdaptor(private val listOfVerifications: MutableList<Verification>,
                          private val showEmptyState: SingleLiveEvent<Boolean>,
                          private val itemSelectedListener: SingleLiveEvent<Verification>
) : RecyclerView.Adapter<GenericViewHolder<*>>() {
    private lateinit var context:Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder<*> {
        context = parent.context
        val binding = VerificationItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VerificationViewHolder(binding, itemSelectedListener)
    }

    override fun onBindViewHolder(holder: GenericViewHolder<*>, position: Int) {
        val item = listOfVerifications[position]
        when(holder){
            is VerificationViewHolder -> item.let { holder.bind(it) }
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemCount(): Int {
        showEmptyState.value = listOfVerifications.size == 0
        return listOfVerifications.size
    }

    fun onNewVerification(newData: List<Verification>){
        listOfVerifications.clear()
        listOfVerifications.addAll(newData)
        notifyDataSetChanged()
        showEmptyState.value = listOfVerifications.size == 0
    }

    class VerificationViewHolder(
        val view: VerificationItemLayoutBinding,
        private val itemSelectedListener: SingleLiveEvent<Verification>
    ) :
        GenericViewHolder<Verification>(view.root)  {
        val listener = itemSelectedListener
        var itemRowBinding: VerificationItemLayoutBinding = view
        private val amount : TextView = itemRowBinding.txtVerificationAmount
        private val name : TextView = itemRowBinding.txtVerificationName
        private val status : TextView = itemRowBinding.txtVerificationStatus
        private val type : TextView = itemRowBinding.txtVerificationType
        private val container : ViewGroup = itemRowBinding.container

        override fun bind(item: Verification) {
            amount.text = "₦0.0"
            name.text = item.verificationContactPersonName.capitalizeWords()
            status.text = item.verificationStatus
            type.text = item.verificationType

            container.setOnClickListener{
                listener.value = item
            }
        }
    }

}
