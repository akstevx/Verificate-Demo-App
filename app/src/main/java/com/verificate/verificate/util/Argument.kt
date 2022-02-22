package com.verificate.verificate.util;

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.Serializable

/**
 * Start an Activity for given class T and allow to work on intent with "run" lambda function
 */

fun <T:Fragment> T.withArguments(vararg arguments: Pair<String, Serializable>): T {
    val bundle = Bundle()
    arguments.forEach { bundle.putSerializable(it.first, it.second) }
    this.arguments = bundle
    return this
}


/**
 * Start an Activity for given class T and allow to work on intent with "run" lambda function
 */
fun <T:BottomSheetDialogFragment> T.withArguments(vararg arguments: Pair<String, Serializable>): T {
    val bundle = Bundle()
    arguments.forEach { bundle.putSerializable(it.first, it.second) }
    this.arguments = bundle
    return this
}


/**
 * Retrieve property from intent
 */
fun <T : Any> FragmentActivity.argument(key: String) = lazy { intent.extras?.get(key) as T }

/**
 * Retrieve property with default value from intent
 */
fun <T : Any> FragmentActivity.argument(key: String, defaultValue: T? = null) = lazy {
    intent.extras?.get(key) as? T ?: defaultValue
}

/**
 * Retrieve property from intent
 */
fun <T : Any> Fragment.argument(key: String) = lazy { arguments?.get(key) as T }
fun <T : Any> Fragment.argumentNullable(key: String) = lazy { arguments?.get(key) as T? }


fun <T : Any> BottomSheetDialogFragment.argument(key: String) = lazy { arguments?.get(key) as T }

/**
 * Retrieve property with default value from intent
 */
fun <T : Any> Fragment.argument(key: String, defaultValue: T? = null) = lazy {
    arguments?.get(key)  as? T ?: defaultValue
}