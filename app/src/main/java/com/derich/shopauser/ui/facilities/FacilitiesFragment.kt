package com.derich.shopauser.ui.facilities

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import com.derich.shopauser.R
import com.derich.shopauser.data.Result
import com.derich.shopauser.databinding.FragmentFacilitiesBinding
import com.derich.shopauser.model.Facilities
import com.derich.shopauser.model.Services
import com.derich.shopauser.ui.products.ProductsFragment
import com.google.firebase.auth.FirebaseAuth

const val FACILITY_NAM = "facilityKey"
class FacilitiesFragment : Fragment(), FacilitiesAdapter.FacilityClickListener {
    private val model: FacilitiesViewModel by navGraphViewModels(R.id.nav_graph_xml)
    private lateinit var binding: FragmentFacilitiesBinding
    private val adapter: FacilitiesAdapter = FacilitiesAdapter(this)
    private var servicesCategory: String?=null
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_facilities, container, false)
        val chosenProduct = arguments?.getParcelable<Services>("serviceKey")
        servicesCategory= chosenProduct!!.serviceName

        binding.handler1 = object : FacilitiesFragmentHandler {
            override fun retry() = loadFacilities()
        }


        binding.swipeToRefreshFacilities.setOnRefreshListener { loadFacilities() }
        binding.listFacilities.adapter = adapter
        checkUser()
        return binding.root
    }

    private fun checkUser() {
        // All user are admins for test's sake.
        binding.isUserSigned = FirebaseAuth.getInstance().currentUser != null
    }

    override fun onStart() {
        super.onStart()
        loadFacilities()
    }

    private fun loadFacilities() {
        model.loadFacilities(servicesCategory!!).observe(viewLifecycleOwner, { result ->
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

    interface FacilitiesFragmentHandler {
        /**
         * in case of failing to load current deals, this give the user option to reload again.
         */
        fun retry()
    }

    override fun onFacilityItemClicked(facility: Facilities) {
        val frag = ProductsFragment()
        val args = Bundle()
        args.putParcelable(FACILITY_NAM, facility)
        frag.arguments = args
        val fragmentManager = parentFragmentManager
        fragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment, frag)
            .commit()
    }
}