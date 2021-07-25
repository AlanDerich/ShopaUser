package com.derich.shopauser.ui.home

import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.derich.shopauser.R
import com.derich.shopauser.data.Result
import com.derich.shopauser.databinding.HomeFragmentBinding
import com.derich.shopauser.model.Services
import com.derich.shopauser.ui.ServicesViewModel
import com.derich.shopauser.ui.facilities.FacilitiesFragment
import com.google.firebase.auth.FirebaseAuth

const val SERVICE_NAM = "serviceKey"
class HomeFragment : Fragment(), ServicesAdapter.ServiceClickListener {

    private val model: ServicesViewModel by navGraphViewModels(R.id.nav_graph_xml)
    private lateinit var binding: HomeFragmentBinding
    private val adapter: ServicesAdapter = ServicesAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)

        binding.handler = object : HomeFragmentHandler {
            override fun retry() = loadDeals()

            override fun openCategories() {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToFacilitiesFragment())
                findNavController()
            }
        }


        binding.swipeToRefresh.setOnRefreshListener { loadDeals() }
        binding.dealsList.adapter = adapter
        checkUser()

        return binding.root
    }

    private fun checkUser() {
        // All user are admins for test's sake.
        binding.isUserSigned = FirebaseAuth.getInstance().currentUser != null
    }

    override fun onStart() {
        super.onStart()
        loadDeals()
    }

    private fun loadDeals() {
        model.loadServices().observe(viewLifecycleOwner, Observer { result ->
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

    interface HomeFragmentHandler {
        /**
         * in case of failing to load current deals, this give the user option to reload again.
         */
        fun retry()
        fun openCategories()
    }
    override fun onServiceItemClicked(service: Services) {
        //When a product item is clicked, pass the product object to the detailsFragment.
        val frag = FacilitiesFragment()
        val args = Bundle()
        args.putParcelable(SERVICE_NAM, service)
        frag.arguments = args
        var fragmentManager = parentFragmentManager
        fragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment, frag)
            .commit()
    }

}