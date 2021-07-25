package com.derich.shopauser.ui.products

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import com.derich.shopauser.R
import com.derich.shopauser.data.Result
import com.derich.shopauser.databinding.FragmentProductsBinding
import com.derich.shopauser.model.Facilities
import com.derich.shopauser.model.Products
import com.derich.shopauser.ui.productdetails.ProductDetailsFragment
import com.google.firebase.auth.FirebaseAuth

//const val FACILITY_NAME_PERSIST = "facilityNAME"
const val SELECTED_PRODUCT_NAM = "productClicked"
class ProductsFragment : Fragment(), ProductsAdapter.ProductClickListener {
    private val model: ProductsViewModel by navGraphViewModels(R.id.nav_graph_xml)
    private lateinit var binding: FragmentProductsBinding
    private val adapter: ProductsAdapter = ProductsAdapter(this)
    private var productsFacility: String?=null
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_products, container, false)
        val chosenFacility = arguments?.getParcelable<Facilities>("facilityKey")
        productsFacility= chosenFacility!!.facilityName

        binding.handler1 = object : ProductsFragmentHandler {
            override fun retry() = loadProducts()
        }


        binding.swipeToRefreshProducts.setOnRefreshListener { loadProducts() }
        binding.listProducts.adapter = adapter
        checkUser()
        return binding.root
    }

    private fun checkUser() {
        // All user are admins for test's sake.
        binding.isUserSigned = FirebaseAuth.getInstance().currentUser != null
    }

    override fun onStart() {
        super.onStart()
        loadProducts()
    }

    private fun loadProducts() {
        model.loadProducts(productsFacility!!).observe(viewLifecycleOwner, { result ->
            binding.result = result
            if (result is Result.Success) {
                adapter.submitList(result.data)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.auth_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    interface ProductsFragmentHandler {
        /**
         * in case of failing to load current deals, this give the user option to reload again.
         */
        fun retry()

    }

    override fun onProductItemClicked(product: Products) {
        val frag = ProductDetailsFragment()
        val args = Bundle()
        args.putParcelable(SELECTED_PRODUCT_NAM, product)
        frag.arguments = args
        val fragmentManager = parentFragmentManager
        fragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment, frag)
            .commit()
    }
}