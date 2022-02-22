package com.verificate.verificate.util

import android.Manifest
import android.Manifest.permission.*
import android.app.Activity
import android.content.Context
import com.github.euzee.permission.PermissionCallback
import com.github.euzee.permission.PermissionUtil
import com.verificate.verificate.R


object CheckPermissionUtil {

    private val LOCATION_PERMISSION_REQ_CODE = 200
    private val LOCATION_PERMISSION_REQ_CODE2 = 2003
    private val WRITE_SD_REQ_CODE = 201
    private val WRITE_SD_REQ_CODE2 = 20122
    private val PICK_CONTACT = 34
    private val PICK_CAMERA = 334
    private val AUDIO_RECORD = 4849

    fun checkLocation(
        activity: Activity,
        callback: com.baurine.permissionutil.PermissionUtil.ReqPermissionCallback
    ) {
        com.baurine.permissionutil.PermissionUtil.checkPermission(
            activity,
            ACCESS_FINE_LOCATION,
            LOCATION_PERMISSION_REQ_CODE,
            activity.getText(R.string.location_req_reason),
            activity.getText(R.string.location_reject_msg),
            callback
        )
    }
    @JvmStatic
    fun camera(
        activity: Activity,
        callback: com.baurine.permissionutil.PermissionUtil.ReqPermissionCallback
    ) {
        com.baurine.permissionutil.PermissionUtil.checkPermission(
            activity,
            CAMERA,
            PICK_CAMERA,
            activity.getText(R.string.camera_req_reason),
            activity.getText(R.string.camera_reject_msg),
            callback
        )
    }

    fun checkWriteSd(
        activity: Activity,
        callback: com.baurine.permissionutil.PermissionUtil.ReqPermissionCallback
    ) {
        com.baurine.permissionutil.PermissionUtil.checkPermission(
            activity,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            WRITE_SD_REQ_CODE,
            "We need write external storage permission to save your location to file",
            "We can't save your location to file without storage permission",
            callback
        )
    }
    @JvmStatic
    fun readWriteSd(
        activity: Activity,
        callback: com.baurine.permissionutil.PermissionUtil.ReqPermissionCallback
    ) {
        com.baurine.permissionutil.PermissionUtil.checkPermission(
            activity,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            WRITE_SD_REQ_CODE,
            "We need read external storage permission to get your picture to file",
            "We can't read you external storage to get your picture",
            callback
        )
    }

    fun checkLocation(
        context: Context,
        callback: PermissionCallback
    ) {
        PermissionUtil.locationBoth(context, callback)

    }
    fun hasFineLocation(
        activity: Activity
    ):Boolean {
       return  com.baurine.permissionutil.PermissionUtil.hasPermission(
            activity,
            ACCESS_FINE_LOCATION
        )
    }

    fun hasCourseLocation(
        activity: Activity
    ):Boolean {
        return  com.baurine.permissionutil.PermissionUtil.hasPermission(
            activity,
            ACCESS_COARSE_LOCATION
        )
    }

    fun initializeLocation(context: Context, permissionDenied:() -> Unit, permissionGranted:() -> Unit) {
        CheckPermissionUtil.checkLocation(context,object: PermissionCallback(){
            override fun onPermissionGranted() {
                permissionGranted()
            }

            override fun onPermissionDenied() {
                //DENIED PERMISSION
                permissionDenied()
            }
        })
    }




    @JvmStatic
    fun camera(
        context: Context,
        callback: PermissionCallback
    ) {
        PermissionUtil.camera(context,callback)
    }
    @JvmStatic
    fun checkWriteSd(
        context: Context,
        callback: PermissionCallback
    ) {
        PermissionUtil.storageRW(context,callback)

    }
    @JvmStatic
    fun readWriteSd(
        context: Context,
        callback: PermissionCallback
    ) {

        PermissionUtil.storageRW(context, callback)
    }
    @JvmStatic
    fun readWriteSdAndCamera(
        context: Context,
        callback: PermissionCallback
    ) {

        PermissionUtil.checkGroup(context,callback, arrayOf(Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE))
    }
    fun checkContacts(
        context: Context,
        callback: PermissionCallback
    ) {
        PermissionUtil.contactsRead(context, callback)

    }
    fun write(
        context: Context,
        callback: PermissionCallback
    ) {
        PermissionUtil.contactsWrite(context,callback)
    }

}
