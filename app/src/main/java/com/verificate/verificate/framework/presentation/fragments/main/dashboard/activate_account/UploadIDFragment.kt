package com.verificate.verificate.framework.presentation.fragments.main.dashboard.activate_account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.verificate.verificate.base.BaseFragment
import com.verificate.verificate.databinding.FragmentUploadIDBinding

class UploadIDFragment : BaseFragment() {
    private var _binding : FragmentUploadIDBinding?  = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding =  FragmentUploadIDBinding.inflate(inflater,container,false)
        return _binding!!.root
    }


}