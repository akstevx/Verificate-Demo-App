package com.verificate.verificate.framework.presentation.fragments.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import com.verificate.verificate.util.Validator
import com.verificate.verificate.base.BaseFragment
import com.verificate.verificate.base.observeChange
import com.verificate.verificate.databinding.FragmentRegistrationBinding
import com.verificate.verificate.framework.presentation.general.*
import com.verificate.verificate.framework.presentation.viewmodels.AuthViewModel
import com.verificate.verificate.util.addNYearsToDate
import com.verificate.verificate.util.capitalize
import java.util.*

class RegistrationFragment : BaseFragment(), DatePickerBottomSheetFragment.OnDatePickedListener,StateBottomsheetFragment.OnReadStateListener,
    ReadGenderBottomSheetFragment.OnReadGenderListener, LGABottomsheetFragment.OnReadLGAListener, ReadGenderBottomSheetFragment.OnBikeStatusListener  {
    private var _binding: FragmentRegistrationBinding?= null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by activityViewModels()
    private val requiredDate: Long by lazy {
        val requiredDate = addNYearsToDate(Date(),-18)
        requiredDate.time
    }
    var isStatePicked =false
    var isLGAPicked =false
    var isDatePicked =false
    var isGenderPicked =false
    var isBikeStatusPicked =false
    var bikeStatus =""
    lateinit var currentState:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        showNavigationBar(toShow = false)
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservers()
        updateViews()
    }
    private fun setObservers(){
        setUpObservers(viewModel)
        viewModel.initiateCreateUserResponse.observeChange(viewLifecycleOwner){
            saveToViewModel()
            mFragmentNavigation.pushFragment(CreatePasswordFragment())
        }

        viewModel.lgaResponse.observeChange(viewLifecycleOwner){
            mFragmentNavigation.openBottomDialogFragment(LGABottomsheetFragment.newInstance(it))
        }
    }

    private fun updateViews() {
        binding.button3.setOnClickListener { mFragmentNavigation.popFragment() }
        binding.button5.setOnClickListener {
            mFragmentNavigation.openBottomSheet(
                    DatePickerBottomSheetFragment.newInstance(
                        caller = "start",
                        dateFormat = "dd-MMM-yyyy",
                        titleText = "Pick Date",
                        buttonText = "Select",
                        date = requiredDate,
                        dateType = DateType.MAX_DATE
                    ) )
        }
        binding.btnSelectState.setOnClickListener { mFragmentNavigation.openBottomDialogFragment(StateBottomsheetFragment()) }
        binding.btnGender.setOnClickListener { mFragmentNavigation.openBottomDialogFragment(ReadGenderBottomSheetFragment()) }
        binding.btnSelectBikeStatus.setOnClickListener { mFragmentNavigation.openBottomDialogFragment(ReadGenderBottomSheetFragment.newInstance(isBikeStatus = true)) }
        binding.btnSelectLGA.setOnClickListener {
            if(!isStatePicked) { showErrorWithAction("Select state first") {
                    mFragmentNavigation.openBottomDialogFragment(StateBottomsheetFragment()) }
            } else viewModel.getLGA(currentState)
        }

        binding.btnGetSecondStep.setOnClickListener {
            if(checkFields()){
                if (binding.rectangleswitch2.isChecked) {
                    viewModel.createUserInit(
                        userFirstName = binding.etFirstName.text.toString(),
                        userLastName = binding.etLastName.text.toString(),
                        email = binding.etEmail.text.toString(),
                        address = binding.etAddress.text.toString(),
                        phoneNumber = binding.etPhoneNumber.text.toString(),
                        bikeStatus = bikeStatus,
                        gender = binding.etGender.text.toString(),
                        state = binding.etState.text.toString(),
                        localGovernment = binding.etLGA.text.toString(),
                        dateOfBirth = binding.etDateOfBirth.text.toString()
                    )
                } else showError("Kindly agree to terms and conditions")
            } else showError("All fields are required to proceed")
        }
    }

    private fun checkFields():Boolean{
        binding.etFirstName.doAfterTextChanged {
            Validator.isValidNameNew(binding.etFirstName)
        }
        binding.etLastName.doAfterTextChanged {
            Validator.isValidNameNew(binding.etLastName)
        }
        binding.etPhoneNumber.doAfterTextChanged {
            Validator.isValidPhone(binding.etPhoneNumber)
        }
        binding.etEmail.doAfterTextChanged {
            Validator.isValidEmail(binding.etEmail)
        }

        return (Validator.isValidNameNew(binding.etFirstName) && Validator.isValidNameNew(binding.etLastName) &&
                Validator.isValidPhone(binding.etPhoneNumber) && Validator.isValidEmail(binding.etEmail) &&
                isBikeStatusPicked && isStatePicked && isLGAPicked && isGenderPicked && isDatePicked)
    }

    override fun saveToViewModel() {
        super.saveToViewModel()
        binding.etFirstName.doAfterTextChanged{
            viewModel.firstName = binding.etFirstName.text.toString()
        }

        binding.etLastName.doAfterTextChanged{
            viewModel.lastName = binding.etLastName.text.toString()
        }

        binding.etPhoneNumber.doAfterTextChanged{
            viewModel.phoneNumber = binding.etPhoneNumber.text.toString()
        }

        binding.etEmail.doAfterTextChanged{
            viewModel.email = binding.etEmail.text.toString()
        }
        viewModel.bikeStatus = bikeStatus
        viewModel.firstName = binding.etFirstName.text.toString()
        viewModel.lastName = binding.etLastName.text.toString()
        viewModel.phoneNumber = binding.etPhoneNumber.text.toString()
        viewModel.gender = binding.etGender.text.toString()
        viewModel.dateOfBirth = binding.etDateOfBirth.text.toString()
        viewModel.lga = binding.etLGA.text.toString()
        viewModel.state = binding.etState.text.toString()
        viewModel.address = binding.etAddress.text.toString()
        viewModel.email = binding.etEmail.text.toString()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onDatePicked(caller: String, date: String) {
        isDatePicked = true
        binding.etDateOfBirth.setText(date)
    }

    override fun onGenderPicked(gender: ReadGenderBottomSheetFragment.GenderObj) {
        isGenderPicked = true
        binding.etGender.setText(gender.genderName)
    }

    override fun onStatePicked(state: String) {
        isStatePicked = true
        binding.etState.setText(state)
        currentState = state.removeSuffix("State").trim().capitalize()
        viewModel.getLGA(currentState)
    }

    override fun onLgaPicked(state: String) {
        isLGAPicked = true
        binding.etLGA.setText(state)
    }

    override fun onStatusPicked(status: String) {
        isBikeStatusPicked = true
        bikeStatus = if (status == "Yes") "Active" else "Inactive"
        binding.etUserBikeStatus.setText(status)
    }
}