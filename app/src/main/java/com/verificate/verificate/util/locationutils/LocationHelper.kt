package com.verificate.verificate.util.locationutils

import android.annotation.SuppressLint
import android.app.Activity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.qucoon.watchguard.utils.locationutils.LocationManager
import com.google.android.gms.location.*
import com.verificate.verificate.util.CheckPermissionUtil

class LocationHelper(var activity: Activity, var locationManager: LocationManager) {
    private val INTERVAL = 1000
    private val FAST_INTERVAL = 100
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var locationCallback: LocationCallback? = null
    private var locationRequest: LocationRequest? = null

    init {
        if (fusedLocationClient == null) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
        }
        createLocationRequest()
        createLocationCallBack()
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest!!.interval = INTERVAL.toLong()
        locationRequest!!.fastestInterval = FAST_INTERVAL.toLong()
        locationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    private fun createLocationCallBack() {
        if (locationCallback == null) {
            locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    super.onLocationResult(locationResult)
                    if (locationResult != null) {
                        for (location in locationResult.locations) {
                            if (location != null) {
                                locationManager.onLocationChanged(location)
                            }
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun startLocationUpdates() {
        if(CheckPermissionUtil.hasCourseLocation(activity) && CheckPermissionUtil.hasFineLocation(activity)){
            fusedLocationClient!!.requestLocationUpdates(locationRequest, locationCallback, null)
            getLastKnownLocation()
        }
    }



    @SuppressLint("MissingPermission")
    private fun getLastKnownLocation() {
        if (fusedLocationClient == null) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.activity)

        }
        if(CheckPermissionUtil.hasCourseLocation(activity) && CheckPermissionUtil.hasFineLocation(activity)){
            fusedLocationClient!!.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    locationManager.getLastKnownLocation(location)
                }
            }
        }
    }

    fun stopLocationUpdates() {
        fusedLocationClient!!.removeLocationUpdates(locationCallback)
    }


}