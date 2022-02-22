package com.verificate.verificate.base

import androidx.lifecycle.*
import com.verificate.verificate.util.SingleLiveEvent
import com.verificate.verificate.util.UseCaseResult
import com.verificate.verificate.util.handleException
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlin.coroutines.CoroutineContext

open class BaseViewModel:ViewModel(), CoroutineScope,LifecycleObserver {
    // Coroutine's background job
    private val job = Job()
    // Define default thread for Coroutine as Main and add job
    override val coroutineContext: CoroutineContext = Dispatchers.Main + job
    val showLoading = MutableLiveData<Boolean>()
    val showError = SingleLiveEvent<String>()
    val showSessionTimeOut= SingleLiveEvent<String>()

//     val paperPrefs: PaperPrefs by inject(PaperPrefs::class.java)
    override fun onCleared() {
        super.onCleared()
        // Clear our job when the linked activity is destroyed to avoid memory leaks
        job.cancel()
    }

    fun <R:Any,T:Any> apiRequest(
        request:R, apiCall:suspend (request:R)-> UseCaseResult<T>, observer: SingleLiveEvent<T>, getError: (response: T) -> String,
        displayLoading:Boolean = true, showErrorMessage:Boolean = true,
        onErrorObserver: SingleLiveEvent<Unit>? = null, customLoader: SingleLiveEvent<Boolean>? = null){
        if(displayLoading)  {
            customLoader?.let {
                it.value = true
            } ?: run {
                showLoading.value = true
            }
        }

        launch {
            val response = withContext(IO){
                apiCall(request)
            }
            if(displayLoading){
                customLoader?.let {
                    it.value = false
                } ?: run {
                    showLoading.value = false
                }

            }
            when(response){
                is UseCaseResult.Success -> observer.value = response.data
                is UseCaseResult.FailedAPI -> {
                    if(showErrorMessage) showError.value = getError(response.data)
                    onErrorObserver?.call()
                }
                is UseCaseResult.Error -> {
                    if(showErrorMessage) showError.value = response.exception.handleException()
                    onErrorObserver?.call()
                }
            }
        }
    }

    fun <T:Any> getApiRequest( apiCall:suspend ()-> UseCaseResult<T>, observer:SingleLiveEvent<T>, getError: (response: T) -> String,
                               displayLoading:Boolean = true,showErrorMessage:Boolean = true,
                               onErrorObserver:SingleLiveEvent<Unit>? = null){
        if(displayLoading)  showLoading.value = true
        launch {
            val response = withContext(IO){apiCall()}
            if(displayLoading)   showLoading.value = false
            when(response){
                is UseCaseResult.Success -> observer.value = response.data
                is UseCaseResult.FailedAPI -> {

                    if(showErrorMessage) showError.value = getError(response.data)
                    onErrorObserver?.call()
                }
                is UseCaseResult.Error -> {
                    if(showErrorMessage) showError.value = response.exception.handleException()
                    onErrorObserver?.call()
                }
            }
        }
    }

    fun <R:Any,T:Any> apiRequest(request:R, apiCall:suspend (request:R)-> UseCaseResult<T>, observer: SingleLiveEvent<T>, getError:(response:T) -> String,
                                 onSuccessOperations:((response:T) -> Unit)? = null, onFailureOperations:( (response:T) -> Unit)? = null, displayLoading:Boolean = true, showErrorMessage:Boolean = true, onErrorObserver: SingleLiveEvent<Unit>? = null
    ){
       if(displayLoading) showLoading.value = true
        launch {
            val response = withContext(IO){apiCall(request)}
            if(displayLoading)   showLoading.value = false
            when(response){
                is UseCaseResult.Success -> {
                    onSuccessOperations?.invoke(response.data)
                    observer.value = response.data
                }
                is UseCaseResult.FailedAPI -> {
                    onFailureOperations?.let {
                        it(response.data)
                    } ?: run{
                        if(showErrorMessage) showError.value = getError(response.data)
                        onErrorObserver?.call()
                    }

                }
                is UseCaseResult.Error -> {
                    if(showErrorMessage) showError.value = response.exception.handleException()
                    onErrorObserver?.call()
                }
            }
        }
    }
}
fun <T> SingleLiveEvent<T>.observeUnit(owner: LifecycleOwner, action:(T?)->Unit){
    this.observe(owner, Observer { action(it) })
}
fun <T> SingleLiveEvent<T>.observeChange(owner: LifecycleOwner, action:(T)->Unit){
    this.observe(owner, Observer { action(it) })
}
fun <T> LiveData<T>.observeChange(owner: LifecycleOwner,action:(T)->Unit){
    this.observe(owner, Observer { action(it) })
}
fun <T> MutableLiveData<T>.observeChange(owner: LifecycleOwner,action:(T)->Unit){
    this.observe(owner, Observer { action(it) })
}