package com.verificate.verificate.base

import android.app.Application
import android.content.Context
import android.location.Location
import com.rilixtech.Country
import io.paperdb.Paper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalStorage {
    @Inject constructor(context: Context){
        Paper.init(context)
        this.context = context
    }

    private lateinit var context: Context

    companion object {

        val IS_FIRST_TIME_LOGIN = "FALSE"
        val AUTH_TOKEN = "AUTH_TOKEN"
        val CUSTOMER_EMAIL = "USER_EMAIL"
        val CUSTOMER_USERNAME = "CUSTOMER_USERNAME"
        val CUSTOMER_PHONENUMBER = "CUSTOMER_PHONENUMBER"
        val USER_ID = "USER_ID"
        val CUSTOMER_COUNTRY= "CUSTOMER_COUNTRY"
        val CUSTOMER_COUNTRYCODE= "CUSTOMER_COUNTRY"
        val CUSTOMER_FULLNAME = "CUSTOERNAME"
        val CUSTOMER_PASSCODE = "CUSTOMER_PASSCODE"
        val HIDE_BALANCE = "false"
        val IS_SIGNED_IN = "IS_SIGNED_IN"
        val IS_LOGGED_IN = "IS_LOGGED_IN"
        val IS_ONLINE= "IS_ONLINE"
        val STATE = "STATE"
        val LGA= "LGA"
        val BIKE_STATUS= "BIKE_STATUS"
        val RATING= "RATING"
        val FIRSTNAME = "FIRSTNAME"
        val LASTNAME = "LASTNAME"
        val ADDRESS = "ADDRESS"
    }

}
fun LocalStorage.savePref(key: String, value: Any){
    when(value){
        is String -> {key.saveStringKey(value)}
        is Boolean -> {key.saveBooleanKey(value)}
        else -> {
            key.saveAnyPref(value)
        }
    }
}


fun LocalStorage.getStringPref(key: String):String{
    return key.getStringKey(key)
}
fun LocalStorage.getBooleanKey(key: String, defaultValue:Boolean):Boolean{
    return key.getBooleanKey(defaultValue)
}

fun <T:Any> LocalStorage.getAnyKey(key: String):T{
    return key.getAnyKey()
}

private fun String.saveStringKey(value:String) = saveStringToKey( this,value)
private fun saveStringToKey(key:String,value:String){
    runBlocking {
        withContext(Dispatchers.IO){
            Paper.book().write(key,value)
        }
    }
}

fun String.getStringKey(default:String):String = getStringFromPref(this,default)
private fun getStringFromPref(key:String,deafult:String):String{
    return runBlocking {
        async(Dispatchers.IO){
            Paper.book().read(key,deafult)
        }.await()
    }
}

private fun String.saveBooleanKey(value:Boolean) =  saveBooleanToKey(this,value)
private fun saveBooleanToKey(key:String,value:Boolean){
    runBlocking {
        withContext(Dispatchers.IO){
            Paper.book().write(key,value)
        }
    }
}

fun String.getBooleanKey(default:Boolean):Boolean = getBooleanFromKey(this,default)
private fun getBooleanFromKey(key:String, defaultvalue:Boolean):Boolean{
    return runBlocking {
        async(Dispatchers.IO){
            Paper.book().read(key,defaultvalue)
        }.await()
    }
}

fun <T:Any> String.saveAnyPref(data:T) = saveAnyToKey(this,data)
private fun <T:Any> saveAnyToKey(key: String, data:T){
    runBlocking {
        withContext(Dispatchers.IO){
            Paper.book().write(key,data)
        }
    }
}
fun <T:Any> String.getAnyKey():T{
    return getAnyFromKey(this)
}
private fun <T:Any> getAnyFromKey(key:String):T{
    return runBlocking {
        async(Dispatchers.IO){
            Paper.book().read(key) as T
        }.await()
    }
}

fun LocalStorage.getUserID():String{
    return getStringFromPref(LocalStorage.USER_ID, "")
}
fun LocalStorage.setUserID(userID:String){
    saveStringToKey(LocalStorage.USER_ID, userID)
}

fun LocalStorage.getCustomerFirstName():String{
    return getStringFromPref(LocalStorage.FIRSTNAME, "")
}
fun LocalStorage.setCustomerFirstname(firstName:String){
    saveStringToKey(LocalStorage.FIRSTNAME, firstName)
}

fun LocalStorage.getCustomerLastName():String{
    return getStringFromPref(LocalStorage.LASTNAME, "")
}
fun LocalStorage.setCustomerLastname(lastName:String){
    saveStringToKey(LocalStorage.LASTNAME, lastName)
}

fun LocalStorage.getCustomerEmail():String{
    return getStringFromPref(LocalStorage.CUSTOMER_EMAIL, "")
}
fun LocalStorage.setCustomerEmail(email:String){
    saveStringToKey(LocalStorage.CUSTOMER_EMAIL, email)
}

fun LocalStorage.getUsername():String{
    return getStringFromPref(LocalStorage.CUSTOMER_USERNAME, "")
}
fun LocalStorage.setUsername(username:String){
    saveStringToKey(LocalStorage.CUSTOMER_USERNAME, username)
}

fun LocalStorage.getCustomerFullName():String{
    return getStringFromPref(LocalStorage.CUSTOMER_FULLNAME, "")
}
fun LocalStorage.setCustomerFullName(username:String){
    saveStringToKey(LocalStorage.CUSTOMER_FULLNAME, username)
}

fun LocalStorage.getAuthorization():String{
    return getStringFromPref(LocalStorage.AUTH_TOKEN, "")
}
fun LocalStorage.setAuthorization(authorization:String){
    saveStringToKey(LocalStorage.AUTH_TOKEN, authorization)
}

fun LocalStorage.getPasscode():String{
    return getStringFromPref(LocalStorage.CUSTOMER_PASSCODE, "")
}
fun LocalStorage.setPasscode(passcode:String){
    saveStringToKey(LocalStorage.CUSTOMER_PASSCODE, passcode)
}

fun LocalStorage.getState():String{
    return getStringFromPref(LocalStorage.STATE, "")
}
fun LocalStorage.setUserState(state:String){
    saveStringToKey(LocalStorage.STATE, state)
}

fun LocalStorage.getLGA():String{
    return getStringFromPref(LocalStorage.LGA, "")
}
fun LocalStorage.setUserLGA(lga:String){
    saveStringToKey(LocalStorage.LGA, lga)
}

fun LocalStorage.getRating():String{
    return getStringFromPref(LocalStorage.RATING, "")
}
fun LocalStorage.setUserRating(lga:String){
    saveStringToKey(LocalStorage.RATING, lga)
}

fun LocalStorage.getBikeStatus():String{
    return getStringFromPref(LocalStorage.BIKE_STATUS, "")
}
fun LocalStorage.setBikeStatus(lga:String){
    saveStringToKey(LocalStorage.BIKE_STATUS, lga)
}

fun LocalStorage.getCustomerPhoneNumber():String{
    return getStringFromPref(LocalStorage.CUSTOMER_PHONENUMBER, "")
}
fun LocalStorage.setCustomerPhoneNumber(phoneNumber:String){
    saveStringToKey(LocalStorage.CUSTOMER_PHONENUMBER, phoneNumber)
}

fun LocalStorage.checkSignedInStatus():Boolean{
    return getBooleanKey(LocalStorage.IS_SIGNED_IN, false)
}
fun LocalStorage.setSignedInStatus(status:Boolean){
    saveBooleanToKey(LocalStorage.IS_SIGNED_IN, status)
}

fun LocalStorage.checkLogInStatus():Boolean{
    return getBooleanKey(LocalStorage.IS_LOGGED_IN, false)
}
fun LocalStorage.setLogInStatus(status:Boolean){
    saveBooleanToKey(LocalStorage.IS_LOGGED_IN, status)
}

fun LocalStorage.checkOnlineStatus():Boolean{
    return getBooleanKey(LocalStorage.IS_ONLINE, false)
}
fun LocalStorage.setOnlineStatus(status:Boolean){
    saveBooleanToKey(LocalStorage.IS_ONLINE, status)
}

fun LocalStorage.getAddress(): Location? {
    return getAnyKey(LocalStorage.ADDRESS)
}
fun LocalStorage.setAddress(address:Location){
    saveAnyToKey(LocalStorage.ADDRESS, address)
}