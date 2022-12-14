package com.verificate.verificate.framework.presentation.fragments.main.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.verificate.verificate.R
import com.verificate.verificate.base.BaseFragment
import com.verificate.verificate.databinding.FragmentPaymentBinding
import com.verificate.verificate.util.ViewPagerObject
import com.verificate.verificate.util.setUpViewPager

class PaymentFragment : BaseFragment() {
    private var _binding: FragmentPaymentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        showNavigationBar(false)
        _binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateUI()
    }

    private fun updateUI() {
        setUpViewer()
        binding.btnCashout.setOnClickListener { mFragmentNavigation.pushFragment(CashoutFragment()) }
    }

    private fun setUpViewer() {
        val listOfPages = listOf(
            ViewPagerObject(ApprovedPaymentFragment(), "Approved"),
            ViewPagerObject(PendingPaymentFragment(), "Pending")
        )
        binding.viewPager.setUpViewPager(listOfPages, childFragmentManager)
        binding.tabs.setupWithViewPager(binding.viewPager)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}