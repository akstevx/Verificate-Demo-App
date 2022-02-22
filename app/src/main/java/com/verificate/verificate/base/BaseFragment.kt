package com.verificate.verificate.base

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.verificate.verificate.R
import com.verificate.verificate.framework.presentation.activities.IntroActivity
import com.verificate.verificate.framework.presentation.general.ErrorBottomsheetFragment
import com.verificate.verificate.framework.presentation.general.SuccessBottomsheetFragment
import com.verificate.verificate.framework.presentation.general.SuccessFragment
import com.verificate.verificate.framework.presentation.general.WarningBottomsheetFragment
import com.verificate.verificate.util.hideKeyboard
//import com.qucoon.alphavibe.views.activities.IntroActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

import kotlin.coroutines.CoroutineContext


open class BaseFragment() : Fragment(), CoroutineScope, SaveData {
    lateinit var localStorage: LocalStorage
    lateinit var mFragmentNavigation: FragmentNavigation
    val backgroudJobs =  Job()
    var isOnline:Boolean = false
    var isLoggedIn:Boolean = false
    var isSignedIn:Boolean = false

    override val coroutineContext: CoroutineContext
        get() = backgroudJobs + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onStart() {
        super.onStart()
        hideKeyboard()
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentNavigation) {
            localStorage = LocalStorage(context = context )
            isOnline = localStorage.checkOnlineStatus()
            isLoggedIn = localStorage.checkLogInStatus()
            isSignedIn = localStorage.checkSignedInStatus()
            mFragmentNavigation = context
        }
    }

//    fun needsUpgrade() =  localStorage.getBooleanKey(LocalStorage.NEEDS_UPGRADE,true)
    interface FragmentNavigation {
        fun pushFragment(fragment: Fragment)
        fun popFragment()
        fun popFragments(n:Int)
        fun openDialogFragment(fragment:DialogFragment)
        fun openBottomDialogFragment(bottomSheetDialogFragment: BottomSheetDialogFragment?)
        fun openBottomSheet(bottomSheetDialogFragment: BottomSheetDialogFragment)
        fun clearStack()
        fun clearDialogFragmentStack()
        fun switchFragment(index: Int)
    }

    override fun onDestroy() {
        super.onDestroy()
        cancelAllJobs()
    }
    fun cancelAllJobs(){
        try{
            backgroudJobs.cancel()
        }catch (ex:Exception){

        }
    }

    fun setUpObservers(viewModel: BaseViewModel){
        setUpErrorHandler(viewModel)
        setUpLoading(viewModel)
    }

    fun setUpErrorHandler(viewModel: BaseViewModel){
        viewModel.showError.observeChange(this){showError(it)}
    }

    fun setUpErrorHandler(viewModel: BaseViewModel, action:()->Unit){
        viewModel.showError.observeChange(this){
            action()
            showError(it)
        }
    }

    fun setUpLoading(viewModel: BaseViewModel){
        viewModel.showLoading.observeChange(this){status -> showLoading(status)}
    }

    fun showLoading(status:Boolean){
        hideKeyboard()
        when(val activity = requireActivity()){
//            is MainActivity -> activity.showLoading(status)
            is IntroActivity -> activity.showLoading(status)
        }
    }

    fun showNavigationBar(toShow:Boolean, title: String?="", canGoBack:Boolean = true){
        if(toShow){
            (activity as AppCompatActivity?)?.supportActionBar?.show()
            (activity as AppCompatActivity?)?.supportActionBar?.title = title
            (activity as AppCompatActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(canGoBack)
            (activity as AppCompatActivity?)?.supportActionBar?.setHomeButtonEnabled(canGoBack)
        } else (activity as AppCompatActivity?)?.supportActionBar?.hide()
    }

    fun showSnackBarWithAction(view: View, text:String, action: ()-> Unit) {
        val snackBar = Snackbar.make(view, text, Snackbar.LENGTH_LONG
        )
        snackBar.setAction("Undo") { action() }
        snackBar.setActionTextColor(resources.getColor(R.color.purple_700))
        snackBar.show()
    }

    fun showSnackBar(view: View, text:String,){
        val snackBar = Snackbar.make(view, text, Snackbar.LENGTH_SHORT)
        snackBar.show()
    }

    fun showToast(errorMessage:String){
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    }

    fun showSuccessWithAction(successMessage:String, action: () -> Unit){
        mFragmentNavigation.openBottomSheet(
            SuccessBottomsheetFragment.newInstance(body = successMessage){
                action()
            })
    }

    fun showSuccess(successMessage:String){
        mFragmentNavigation.openBottomSheet(SuccessBottomsheetFragment.newInstance(body = successMessage))
    }
    fun displaySuccess(successTitle:String, successMessage:String, action: () -> Unit){
        mFragmentNavigation.openDialogFragment(
            SuccessFragment.newInstance(successTitle, successMessage) { action() })
    }


    fun showWarning(errorMessage:String, action: () -> Unit ){
        mFragmentNavigation.openBottomSheet(WarningBottomsheetFragment.newInstance(errorMessage) { action() })
    }

    fun showErrorWithAction(errorMessage:String, action: () -> Unit){
        mFragmentNavigation.openBottomSheet(ErrorBottomsheetFragment.newInstance("Error!", errorMessage) { action() })
    }

    fun showError(errorMessage:String){
        mFragmentNavigation.openBottomSheet(ErrorBottomsheetFragment.newInstance("Error!", errorMessage))
    }

    override fun saveToViewModel() {

    }

}
interface SaveData {
    fun saveToViewModel()
}
