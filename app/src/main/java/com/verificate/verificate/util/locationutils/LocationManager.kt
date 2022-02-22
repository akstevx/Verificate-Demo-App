package com.qucoon.watchguard.utils.locationutils

import android.location.Location

interface LocationManager {
    fun onLocationChanged(location: Location?)

    fun getLastKnownLocation(location: Location?)
}