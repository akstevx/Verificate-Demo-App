package com.verificate.verificate.framework.presentation.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.verificate.verificate.R
import com.verificate.verificate.base.BaseFragment
import com.verificate.verificate.base.checkLogInStatus
import com.verificate.verificate.base.checkSignedInStatus
import com.verificate.verificate.framework.presentation.activities.IntroActivity
import com.verificate.verificate.framework.presentation.activities.MainActivity
import com.verificate.verificate.util.delayFor

class SplashFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        delayFor(3000){
            when(localStorage.checkLogInStatus()) {
                true -> {
                    val myIntent = Intent(activity, MainActivity::class.java)
                    startActivity(myIntent)
                }
                else -> {
                    if(localStorage.checkSignedInStatus())
                        mFragmentNavigation.switchFragment(IntroActivity.LOGIN)
                    else mFragmentNavigation.switchFragment(IntroActivity.GETSTARTED)
                }
            }
        }
    }


}