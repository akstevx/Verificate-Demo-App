package com.verificate.verificate.framework.presentation.fragments.main.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.verificate.verificate.R
import com.verificate.verificate.base.BaseFragment
import com.verificate.verificate.base.getUsername
import com.verificate.verificate.databinding.FragmentMoreBinding
import com.verificate.verificate.framework.presentation.general.LogoutDialogFragment
import com.verificate.verificate.util.updateRecycler

class MoreFragment : BaseFragment() {
    private var _binding: FragmentMoreBinding?= null
    private val binding get() = _binding!!

    private data class MoreItems(
        val itemName:String,
        val itemIcon:Int
    )

    private val listOfItems = listOf<MoreItems>(
        MoreItems("View Profile", R.drawable.view_profile_ic),
        MoreItems("Help & Support", R.drawable.support_ic),
        MoreItems("About Verificate", R.drawable.about_ic)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        showNavigationBar(false)
        _binding = FragmentMoreBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateViews()
    }

    private fun updateViews() {
        setUpRecycler()
        binding.txtProfileName.text = localStorage.getUsername()
        binding.btnLogout.setOnClickListener {
            mFragmentNavigation.openBottomDialogFragment(LogoutDialogFragment())
        }
    }

    private fun setUpRecycler() {
        binding.recyclerItems.updateRecycler(requireContext(), listOfItems, R.layout.profile_item_layout, listOf(R.id.textView31, R.id.imageView16),
            { innerViews, position ->
                val icon = innerViews[R.id.imageView16] as ImageView
                val name = innerViews[R.id.textView31] as TextView
                val item = listOfItems[position]

                name.text = item.itemName
                icon.setImageResource(item.itemIcon)

            }, { position ->
                val item = listOfItems[position]
                when(item.itemName){
                    "View Profile" -> mFragmentNavigation.pushFragment(ProfileFragment())
                    "Help & Support" -> mFragmentNavigation.pushFragment(SupportFragment())
                    "About Verificate" -> {}
                }
            })

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}