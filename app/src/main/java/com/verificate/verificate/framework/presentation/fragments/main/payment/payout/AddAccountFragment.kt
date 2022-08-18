package com.verificate.verificate.framework.presentation.fragments.main.payment.payout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.verificate.verificate.R
import com.verificate.verificate.base.BaseFragment
import com.verificate.verificate.databinding.FragmentAddAccountBinding

class AddAccountFragment : BaseFragment() {
    private var _binding: FragmentAddAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        showNavigationBar(true, title = "Add account")
        _binding = FragmentAddAccountBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddAccount.setOnClickListener {
            displaySuccess(successMessage = "Your payout account has been \n" + "added succesfully", action = {
                mFragmentNavigation.clearStack()
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}