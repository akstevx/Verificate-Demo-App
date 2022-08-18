package com.verificate.verificate.framework.presentation.fragments.main.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.verificate.verificate.base.BaseFragment
import com.verificate.verificate.databinding.FragmentCashoutBinding
import com.verificate.verificate.framework.presentation.fragments.main.payment.payout.PayoutAccountFragment
import com.verificate.verificate.framework.presentation.fragments.main.payment.payout.RecentPayoutFragment
import com.verificate.verificate.util.ViewPagerObject
import com.verificate.verificate.util.setUpViewPager

class CashoutFragment : BaseFragment() {
    private var _binding: FragmentCashoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        showNavigationBar(true, title = "Cashout")
        _binding = FragmentCashoutBinding.inflate(inflater, container, false)
        return _binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateUI()
    }

    private fun updateUI() {
        setUpViewer()
    }

    private fun setUpViewer() {
        val listOfPages = listOf(
            ViewPagerObject(PayoutAccountFragment(), "Payout Account"),
            ViewPagerObject(RecentPayoutFragment(), "Recent Payouts")
        )
        binding.viewPager.setUpViewPager(listOfPages, childFragmentManager)
        binding.tabs.setupWithViewPager(binding.viewPager)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}