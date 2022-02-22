package com.verificate.verificate.framework.presentation.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.ncapdevi.fragnav.FragNavController
import com.verificate.verificate.R
import com.verificate.verificate.base.BaseActivity
import com.verificate.verificate.framework.presentation.fragments.main.dashboard.DashboardFragment
import com.verificate.verificate.framework.presentation.fragments.main.map.LocationFragment
import com.verificate.verificate.framework.presentation.fragments.main.more.MoreFragment
import com.verificate.verificate.framework.presentation.fragments.main.payment.PaymentFragment
import com.verificate.verificate.services.BackgroundService
import com.verificate.verificate.util.delayFor
import com.verificate.verificate.util.hide
import com.verificate.verificate.util.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    @Inject lateinit var backgroundService: BackgroundService

    val baseFragment = lazy {
        listOf(
            DashboardFragment(), LocationFragment(),
            PaymentFragment(), MoreFragment()
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        backgroundService.startVerificationWorker()

        supportActionBar?.hide()
        intFragNav()
        setUpBottomBar()
    }

    private fun intFragNav() {
        initFragNavController(this,baseFragment.value,"MAIN ACTIVITY",supportFragmentManager,R.id.content)
        delayFor(100){
            switchFragment(DASHBOARD)
        }
    }

    private fun setUpBottomBar() {
        bottomNavigation.selectedItemId = R.id.navigation_home
        bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> switchFragment(DASHBOARD)
                R.id.navigation_map -> switchFragment(MAP)
                R.id.navigation_pay -> switchFragment(PAYMENT)
                R.id.navigation_more -> switchFragment(MORE)
                else -> false
            }
            true
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onTabTransaction(fragment: Fragment?, index: Int) {
        super.onTabTransaction(fragment, index)
        actionBarOperations(fragment)
    }
    override fun onFragmentTransaction(
        fragment: Fragment?,
        transactionType: FragNavController.TransactionType
    ) {
        super.onFragmentTransaction(fragment, transactionType)
        actionBarOperations(fragment)
    }


    private fun controlBottomBar(fragment: Fragment){
        val isNotARootFragment = baseFragment.value.filter { it::class == fragment::class }.isNullOrEmpty()
        hideBottomBar(isNotARootFragment)
    }

    private fun actionBarOperations(fragment: Fragment?) {
        fragment?.let { controlBottomBar(it) }
    }

    private fun showBottomBar() {
        bottomNavigation.show()
    }

    private fun hideBottomBar() {
        bottomNavigation.hide()
    }
    private fun hideBottomBar(toHide:Boolean){
        if(!toHide) showBottomBar() else hideBottomBar()
    }

    companion object{
        val DASHBOARD = FragNavController.TAB1
        val MAP = FragNavController.TAB2
        val PAYMENT = FragNavController.TAB3
        val MORE = FragNavController.TAB4
    }
}