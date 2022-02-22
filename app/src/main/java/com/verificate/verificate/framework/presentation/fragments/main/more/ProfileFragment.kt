package com.verificate.verificate.framework.presentation.fragments.main.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.verificate.verificate.base.BaseFragment
import com.verificate.verificate.base.observeChange
import com.verificate.verificate.databinding.FragmentProfileBinding
import com.verificate.verificate.framework.presentation.general.LGABottomsheetFragment
import com.verificate.verificate.framework.presentation.general.StateBottomsheetFragment
import com.verificate.verificate.framework.presentation.viewmodels.AuthViewModel
import com.verificate.verificate.util.capitalize

class ProfileFragment : BaseFragment(), LGABottomsheetFragment.OnReadLGAListener,
    StateBottomsheetFragment.OnReadStateListener {
    private var _binding: FragmentProfileBinding?= null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by activityViewModels()

    private var isStatePicked =false
    private var isLGAPicked =false
    private lateinit var currentState:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        showNavigationBar(true, "My profile")
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateViews()
        setObservers()
    }

    private fun setObservers() {
        setUpObservers(authViewModel)
        authViewModel.lgaResponse.observeChange(viewLifecycleOwner){
            mFragmentNavigation.openBottomDialogFragment(LGABottomsheetFragment.newInstance(it))
        }
    }

    private fun updateViews() {
        binding.btnSelectState2.setOnClickListener { mFragmentNavigation.openBottomDialogFragment(StateBottomsheetFragment()) }
        binding.btnSelectLGA2.setOnClickListener {
            if(!isStatePicked) { showErrorWithAction("Select state first") {
                mFragmentNavigation.openBottomDialogFragment(StateBottomsheetFragment()) }
            } else authViewModel.getLGA(currentState)
        }
    }

    override fun onLgaPicked(state: String) {
        isLGAPicked = true
        binding.etLGA.setText(state)
    }

    override fun onStatePicked(state: String) {
        isStatePicked = true
        binding.etState.setText(state)
        currentState = state.removeSuffix("State").trim().capitalize()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}