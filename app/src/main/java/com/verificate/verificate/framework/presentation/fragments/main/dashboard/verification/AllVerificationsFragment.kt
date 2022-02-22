package com.verificate.verificate.framework.presentation.fragments.main.dashboard.verification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.verificate.verificate.base.BaseFragment
import com.verificate.verificate.base.observeChange
import com.verificate.verificate.databinding.FragmentAllVerificationsBinding
import com.verificate.verificate.framework.presentation.viewmodels.VerificationViewModel
import com.verificate.verificate.model.response.verification.Verification
import com.verificate.verificate.util.SingleLiveEvent
import com.verificate.verificate.util.hide
import com.verificate.verificate.util.show

class AllVerificationsFragment : BaseFragment() {
    private var _binding: FragmentAllVerificationsBinding?= null
    private val binding get() = _binding!!

    private val verificationViewModel: VerificationViewModel by activityViewModels()
    private val itemSelectedListener: SingleLiveEvent<Verification> =  SingleLiveEvent()
    private val showEmptyState: SingleLiveEvent<Boolean> = SingleLiveEvent()
    private val adaptor: VerificationAdaptor by lazy { VerificationAdaptor(mutableListOf(),showEmptyState,itemSelectedListener) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        showNavigationBar(true, "Verification Request")
        _binding = FragmentAllVerificationsBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateViews()
    }

    private fun updateViews() {
        verificationViewModel.displayVerifications().observeChange(viewLifecycleOwner){
            adaptor.onNewVerification(it)
            setUpRecycler()
        }

        showEmptyState.observeChange(viewLifecycleOwner){
            if(it){
                binding.ivEmptyState.show()
                binding.txtEmptyState.show()
                binding.recyclerView.hide()
            }else{
                binding.ivEmptyState.hide()
                binding.txtEmptyState.hide()
                binding.recyclerView.show()
            }
        }

        itemSelectedListener.observeChange(viewLifecycleOwner){
//            showError(it.verificationContactPersonName)
        }

    }

    private fun setUpRecycler() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.recyclerView.adapter = adaptor
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}