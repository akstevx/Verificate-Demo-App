package com.verificate.verificate.base

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.verificate.verificate.base.BaseFragment
import dmax.dialog.SpotsDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BaseBottomSheetDialogFragment: BottomSheetDialogFragment(), CoroutineScope {
    lateinit var mFragmentNavigation: BaseFragment.FragmentNavigation
    lateinit  var alertDialog: AlertDialog
    val backgroudJobs =  Job()
    override val coroutineContext: CoroutineContext
        get() = backgroudJobs + Dispatchers.Main
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseFragment.FragmentNavigation) {
            mFragmentNavigation = context
            alertDialog = SpotsDialog.Builder()
                .setContext(context)
                .setMessage("Loading...")
                .setCancelable(false)
                .build()

        }
    }
    override fun onDestroy() {
        super.onDestroy()
        cancelAllJobs()
    }

    fun clearStackAndDismiss(){
        dialog?.dismiss()
        mFragmentNavigation.clearDialogFragmentStack()
    }

    fun cancelAllJobs(){
        try{
            backgroudJobs.cancel()
        }catch (ex:Exception){

        }
    }

    fun showLoading(status:Boolean){
        if (status) alertDialog.show() else alertDialog.dismiss()
    }

    fun showError(errorMessage:String){
        Toast.makeText(requireContext(),errorMessage, Toast.LENGTH_SHORT).show()
        // mFragmentNavigation.openBottomSheet(SuccessBottomSheetFragment.getInstance(false,"Failed",errorMessage))
    }
}