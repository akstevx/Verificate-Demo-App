package com.verificate.verificate.framework.presentation.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import com.ncapdevi.fragnav.FragNavController
import com.verificate.verificate.R
import com.verificate.verificate.base.BaseActivity
import com.verificate.verificate.base.BaseFragment
import com.verificate.verificate.framework.presentation.fragments.IntroFragment
import com.verificate.verificate.framework.presentation.fragments.SplashFragment
import com.verificate.verificate.framework.presentation.fragments.authentication.LoginFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_intro.*

@AndroidEntryPoint
class IntroActivity : BaseActivity() {
    val baseFragment = lazy {
        listOf<BaseFragment>(SplashFragment(), IntroFragment(), LoginFragment())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)


        supportActionBar?.hide()
        initFragNavController(this, baseFragment.value, TAG, supportFragmentManager, R.id.content)
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

    fun showLoading(b: Boolean) {
        if (b) {
            loadingRoot.visibility = View.VISIBLE
            loadingView.startRippleAnimation()
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        } else {
            loadingRoot.visibility = View.GONE
            loadingView.stopRippleAnimation()
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }

    companion object{
        val TAG = "Intro Activity"
        val GETSTARTED = FragNavController.TAB2
        val LOGIN = FragNavController.TAB3
    }
}