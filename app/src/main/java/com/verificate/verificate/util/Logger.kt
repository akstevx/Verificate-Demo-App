package com.verificate.verificate.util

import android.util.Log
import com.verificate.verificate.BuildConfig.DEBUG
import com.verificate.verificate.util.Constants.TAG
import timber.log.Timber

var isUnitTest = false

fun printLog(className: String?, message: String ) {
    if (DEBUG && !isUnitTest) {
        Log.d(TAG, "$className: $message")
        Timber.e(message)
    }
    else if(DEBUG && isUnitTest){
        println("$className: $message")
    }

}

fun cLog(msg: String?){
    msg?.let {
        if(!DEBUG){
//                FirebaseCrashlytics.getInstance().log(it)
        }
    }

}