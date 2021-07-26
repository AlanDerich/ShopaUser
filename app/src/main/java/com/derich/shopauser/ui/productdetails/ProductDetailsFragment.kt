package com.derich.shopauser.ui.productdetails

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.derich.shopauser.R
import com.derich.shopauser.databinding.FragmentProductDetailsBinding
import com.derich.shopauser.model.Products
import com.derich.shopauser.util.MyCustomDialog

class ProductDetailsFragment : Fragment(), View.OnClickListener {
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
        binding.buttonAddProductToCart.setOnClickListener(this)
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
    private fun addToCartDialog(view: View){
        MyCustomDialog().show(parentFragmentManager, "MyCustomFragment")
    }

    override fun onClick(v: View?) {
        val button = v as Button
        when(button.id){
            R.id.buttonAddProductToCart->{
                addToCartDialog(v)
            }
        }
    }
}