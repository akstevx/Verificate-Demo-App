package com.verificate.verificate.framework.presentation.fragments.main.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.verificate.verificate.R
import com.verificate.verificate.base.*
import com.verificate.verificate.databinding.FragmentDashboadBinding
import com.verificate.verificate.framework.presentation.fragments.main.dashboard.activate_account.ActivateAccountFragment
import com.verificate.verificate.framework.presentation.fragments.main.dashboard.verification.AllVerificationsFragment
import com.verificate.verificate.framework.presentation.fragments.main.dashboard.verification.ApprovedVerificationBottomsheetFragment
import com.verificate.verificate.framework.presentation.fragments.main.dashboard.verification.VerificationAdaptor
import com.verificate.verificate.framework.presentation.fragments.main.dashboard.verification.VerificationBottomsheetFragment
import com.verificate.verificate.framework.presentation.fragments.main.map.LocationFragment
import com.verificate.verificate.framework.presentation.viewmodels.AgentViewModel
import com.verificate.verificate.framework.presentation.viewmodels.VerificationViewModel
import com.verificate.verificate.model.response.verification.Verification
import com.verificate.verificate.util.CheckPermissionUtil
import com.verificate.verificate.util.SingleLiveEvent
import com.verificate.verificate.util.hide
import com.verificate.verificate.util.show
import timber.log.Timber
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import com.google.firebase.firestore.GeoPoint
import java.io.IOException
import java.util.*


class DashboardFragment : BaseFragment() {
    private var _binding: FragmentDashboadBinding?= null
    private val binding get() = _binding!!
    private var longitude:Double? =null
    private var latitude:Double? =null
    private var mLongitude:Double? =null
    private var mLatitude:Double? =null
    private lateinit var  geocoder : Geocoder

    private val agentViewModel: AgentViewModel by activityViewModels()
    private val verificationViewModel: VerificationViewModel by activityViewModels()
    private val itemSelectedListener = SingleLiveEvent<Verification>()
    private val showEmptyState = SingleLiveEvent<Boolean>()
    private lateinit var currentVerification:Verification
    private val adaptor: VerificationAdaptor by lazy { VerificationAdaptor(mutableListOf(),showEmptyState,itemSelectedListener) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        geocoder = Geocoder(requireContext(), Locale.getDefault())
        showNavigationBar(false)
        _binding = FragmentDashboadBinding.inflate(inflater, container, false)
        return _binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeData()
        setObservers()
        updateViews()
        controlSwitch()
    }

    private fun initializeData(){
        if (!verificationViewModel.hasVerificationsLoaded){
            verificationViewModel.hasVerificationsLoaded = true
            verificationViewModel.getAssignedVerifications()
//            binding.swipeToRefresh.post { binding.swipeToRefresh.isRefreshing = true }
        }
    }

    private fun updateViews() {
        binding.textView.text = "Hi, ${localStorage.getCustomerFirstName()}"
        binding.verificationContainer.setOnClickListener { mFragmentNavigation.pushFragment(ActivateAccountFragment()) }
        binding.btnViewRequests.setOnClickListener { mFragmentNavigation.pushFragment(AllVerificationsFragment()) }
    }

    private fun setObservers(){
        setUpObservers(verificationViewModel)
        setUpRefresher()

        verificationViewModel.displayVerifications().observeChange(viewLifecycleOwner){
            adaptor.onNewVerification(it.take(5))
            setUpRecycler()
        }

        verificationViewModel.getAllVerifications.observeChange(viewLifecycleOwner){
            binding.swipeToRefresh.isRefreshing = false
        }

        showEmptyState.observeChange(viewLifecycleOwner){
            if(it){
                binding.ivEmptyState.show()
                binding.txtEmptyState.show()
                binding.recyclerView.hide()
            }else{
                binding.ivEmptyState.hide()
                binding.txtEmptyState.hide()
                binding.recyclerView.show()
            }
        }

        itemSelectedListener.observeChange(viewLifecycleOwner){
            if(CheckPermissionUtil.hasFineLocation(requireActivity())){
                mFragmentNavigation.openBottomDialogFragment(VerificationBottomsheetFragment.newInstance(it))
            } else enableLocation()
        }

        VerificationBottomsheetFragment.onActionSelected.observeChange(viewLifecycleOwner){
            //Update Status API
            currentVerification = it.second
            when (it.first) {
                VerificationBottomsheetFragment.ACTION.ACCEPT -> {
                    verificationViewModel.makeVerificationAction(
                        verificationId = currentVerification.verificationId,
                        verificationStatus = VerificationViewModel.VerificationStatus.ACCEPT
                    )
                }
                VerificationBottomsheetFragment.ACTION.DECLINE -> {
                    verificationViewModel.makeVerificationAction(
                        verificationId = currentVerification.verificationId,
                        verificationStatus = VerificationViewModel.VerificationStatus.DECLINE
                    )
                }
            }
        }

        verificationViewModel.verificationActionResponse.observeChange(viewLifecycleOwner){
            mFragmentNavigation.openBottomDialogFragment(ApprovedVerificationBottomsheetFragment.newInstance(currentVerification))
        }

        VerificationBottomsheetFragment.onLocateOnMap.observeChange(viewLifecycleOwner){
            verificationViewModel.currentVerification = it

//            longitude = getLocationFromAddress(it.verificationAddress)?.longitude
//            latitude = getLocationFromAddress(it.verificationAddress)?.latitude
//
//            localStorage.getAddress()?.let { currentLocation ->
//                mLongitude = currentLocation.longitude
//                mLatitude = currentLocation.latitude
//            }

            Timber.e("ADDRESS IS: ${it.verificationAddress} LOCATION: $mLongitude,$mLatitude AND DESTINATION: $longitude,$latitude")

            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?saddr=6.41,3.5510172&daddr=6.4392712,3.5480172")
            )
            startActivity(intent)
        }

        ApprovedVerificationBottomsheetFragment.onLocateOnMap.observeChange(viewLifecycleOwner){
            verificationViewModel.currentVerification = it
            mFragmentNavigation.pushFragment(LocationFragment.newInstance(it))
        }

    }

    private fun enableLocation(){
        CheckPermissionUtil.initializeLocation(requireContext(), {
            showError("You will be unable to verify items until your location is enabled") //To change body of created functions use File | Settings | File Templates.
        }, {
            showSuccess("Location Permission has been granted, you can verify items now")
        })
    }


    private fun setUpRefresher() {
        verificationViewModel.showError.observe(viewLifecycleOwner, Observer {
            binding.swipeToRefresh.isRefreshing = false
        })

        binding.swipeToRefresh.setOnRefreshListener {
            verificationViewModel.getAssignedVerifications()
            binding.swipeToRefresh.isRefreshing = false
        }
    }

    private fun setUpRecycler() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.recyclerView.adapter = adaptor
    }

    private fun controlSwitch() {
        if (!localStorage.checkOnlineStatus()) binding.button2.hide()
        binding.statusSwitch.isChecked = localStorage.checkOnlineStatus()

        binding.button2.setOnClickListener {
            Timber.e("LOCAL STATUS---> ${localStorage.checkOnlineStatus()}")
            if (localStorage.checkOnlineStatus()) {
                showWarning(resources.getString(R.string.offline_warning)){
                    binding.statusSwitch.isChecked = false
                }
            }
        }

        binding.statusSwitch.setOnCheckedChangeListener { compoundButton, b ->
            when(b){
                true -> {
                    compoundButton.text = "Go offline  "
                    binding.button2.show()
                    agentViewModel.updateAgentsStatus(AgentViewModel.Status.ACTIVATE)
                }
                false -> {
                    compoundButton.text = "Come online  "
                    binding.button2.hide()
                    agentViewModel.updateAgentsStatus(AgentViewModel.Status.DEACTIVATE)
                }
            }
            localStorage.setOnlineStatus(b)
            Timber.e("LOCAL STATUS---> ${localStorage.checkOnlineStatus()}")
        }
    }

    fun getLocationFromAddress(strAddress: String?): GeoPoint? {
//        val coder = Geocoder(this)
        val address: List<Address>?
        var p1: GeoPoint? = null

        try {
            address = geocoder.getFromLocationName(strAddress, 1)
            if (address == null) {

                return null
            }
            val location: Address = address[0]
            location.latitude
            location.longitude
            p1 = GeoPoint(
                (location.latitude * 1E6),
                (location.longitude * 1E6)
            )
            return p1
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}