package com.derich.shopauser.ui.productdetails

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.derich.shopauser.R
import com.derich.shopauser.databinding.FragmentProductDetailsBinding
import com.derich.shopauser.model.Products

class ProductDetailsFragment : Fragment() {
    private var chosenProduct:Products?=null
    private lateinit var binding: FragmentProductDetailsBinding
    //    val safeArgs: FacilitiesFragmentArgs by navArgs()
//    val categoryName= safeArgs.serviceTitle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_details, container, false)
        chosenProduct = arguments?.getParcelable("productClicked")
        checkUser()
        return binding.root
    }

    private fun checkUser() {
        // All user are admins for test's sake.
        binding.product=chosenProduct
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.auth_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}