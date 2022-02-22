package com.verificate.verificate.framework.presentation.fragments.main.more

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.verificate.verificate.R
import com.verificate.verificate.base.BaseFragment
import com.verificate.verificate.databinding.FragmentSupportBinding
import com.verificate.verificate.util.updateRecycler

class SupportFragment : BaseFragment() {
    private var _binding: FragmentSupportBinding?= null
    private val binding get() = _binding!!

    private data class SupportItem(
        val itemName:String,
        val itemIcon:Int
    )

    private val listOfItems = listOf<SupportItem>(
        SupportItem("Contact us on Instagram", R.drawable.insta_ic),
        SupportItem("Contact us on Twitter", R.drawable.twitter_ic),
        SupportItem("Contact us via Email", R.drawable.email_ic)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        showNavigationBar(true, "Help & Support")
        _binding = FragmentSupportBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecycler()
    }

    private fun setUpRecycler() {
        binding.recyclerView.updateRecycler(requireContext(), listOfItems, R.layout.support_item_layout, listOf(R.id.textView33, R.id.imageView20),
            { innerViews, position ->
                val icon = innerViews[R.id.imageView20] as ImageView
                val name = innerViews[R.id.textView33] as TextView
                val item = listOfItems[position]

                name.text = item.itemName
                icon.setImageResource(item.itemIcon)

            }, { position ->
                val item = listOfItems[position]
                when(item.itemName){

                }
            })

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}