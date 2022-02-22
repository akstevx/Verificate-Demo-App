package com.verificate.verificate.framework.presentation.fragments.main.map

import android.annotation.SuppressLint
import android.content.res.Resources
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.github.euzee.permission.PermissionCallback
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.google.android.libraries.places.api.Places
import com.qucoon.watchguard.utils.locationutils.LocationManager
import com.verificate.verificate.BuildConfig
import com.verificate.verificate.R
import com.verificate.verificate.base.BaseFragment
import com.verificate.verificate.base.savePref
import com.verificate.verificate.base.setAddress
import com.verificate.verificate.databinding.FragmentLocationBinding
import com.verificate.verificate.framework.presentation.activities.MainActivity
import com.verificate.verificate.framework.presentation.viewmodels.VerificationViewModel
import com.verificate.verificate.model.response.verification.Verification
import com.verificate.verificate.util.CheckPermissionUtil
import com.verificate.verificate.util.argumentNullable
import com.verificate.verificate.util.locationutils.LocationHelper
import com.verificate.verificate.util.withArguments

class LocationFragment : BaseFragment(), OnMapReadyCallback, LocationManager{
    private val verificationViewModel: VerificationViewModel by activityViewModels()
    private var _binding: FragmentLocationBinding?= null
    private val binding get() = _binding!!
    private val verification:Verification? by argumentNullable(VERIFICATION)
    private var service: android.location.LocationManager? = null

    var mGoogleMap: GoogleMap? = null
    private var permissionDenied = false
    var mapView: MapView? = null
    private var lastLatLng:LatLng? = null
    var lastLocation:Location? = null
    private lateinit var locationHelper: LocationHelper
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mapView = parentFragmentManager.findFragmentById(R.id.map) as MapView?
//        mapView = view?.findViewById<View>(R.id.map) as MapView
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        showNavigationBar(false)
        _binding = FragmentLocationBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Places.initialize(requireContext(), BuildConfig.MAPS_API_KEY)
        checkLocation()
        initializeLocation()
    }

    override fun onStart() {
        super.onStart()
        checkLocation()
    }

    private fun checkLocation() {
        if (!CheckPermissionUtil.hasFineLocation(requireActivity())) {
            CheckPermissionUtil.initializeLocation(requireContext(), {
                showError("You will be unable to verify items until your location is enabled") //To change body of created functions use File | Settings | File Templates.
            }, {
                showSuccess("Location Permission has been granted, you can verify items now")
            })
        }
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        MapsInitializer.initialize(context)
        mGoogleMap = googleMap
        mGoogleMap?.isMyLocationEnabled = true
        mGoogleMap?.uiSettings?.isZoomControlsEnabled = false
        mGoogleMap?.uiSettings?.isZoomGesturesEnabled = true
        mGoogleMap?.uiSettings?.isMyLocationButtonEnabled = false
        try {
            val success = mGoogleMap!!.setMapStyle (
                MapStyleOptions .loadRawResourceStyle (
                    context , R.raw.jodimap))

            if ( ! success) {
                Log .e ( " MapsActivity " , " Style parsing failed. " )
            }
        } catch ( e :  Resources. NotFoundException ) {
            Log .e ( " MapsActivity " , " Can't find style. Error: " , e)
        }

        addMarker(6.436160, 3.523290)

    }

    private fun initializeLocation() {
        CheckPermissionUtil.checkLocation(requireContext(),object:PermissionCallback(){
            override fun onPermissionGranted() {
/*                locationHelper = LocationHelper(requireActivity(), this@LocationFragment)
                locationHelper.startLocationUpdates()*/
                mapView?.let { map ->
                    map.onCreate(null)
                    map.onResume()
                    // Set the map ready callback to receive the GoogleMap object
                    map.getMapAsync(this@LocationFragment)
                }
            }

            override fun onPermissionDenied() {
                showError("You will be unable to verify items until your location is enabled") //To change body of created functions use File | Settings | File Templates.
            }
        })
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity as MainActivity)

    }

    private fun addMarker(latitude:Double, longitude:Double){
        val latLng = LatLng(latitude, longitude)
        val icon = BitmapDescriptorFactory.fromResource(R.drawable.small_logo)
        mGoogleMap?.addMarker(MarkerOptions().position(latLng).title("Marker Title").icon(icon).snippet("Marker Description"))
    }

    override fun onDestroy() {
        super.onDestroy()
//        locationHelper.stopLocationUpdates()
        _binding = null
    }

    override fun onLocationChanged(location: Location?) {
        location?.let{ localStorage.setAddress(it) }
        setCameraOnLocation(location)
    }

    override fun getLastKnownLocation(location: Location?) {
        setCameraOnLocation(location)
    }

    private fun setCameraOnLocation(location: Location?) {
        location?.let {
            lastLocation = it
            val currentLatLng = LatLng(location.latitude, location.longitude)
            lastLatLng = currentLatLng
            mGoogleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
        }
    }

    private fun zoomInMap(latitude:Double, longitude:Double){
        val latLng = LatLng(latitude, longitude)
        val cameraPosition = CameraPosition.Builder().target(latLng).zoom(15f).build()
        mGoogleMap!!.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    private fun setNewLocation(latLng: LatLng) {
        zoomInMap(latLng.latitude,latLng.longitude)
    }


    companion object {
        val VERIFICATION = "verification"

        fun newInstance(verification: Verification): LocationFragment {
            return LocationFragment().withArguments(VERIFICATION to verification)
        }
    }
}