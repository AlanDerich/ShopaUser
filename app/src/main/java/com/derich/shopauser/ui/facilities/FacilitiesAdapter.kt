package com.derich.shopauser.ui.facilities

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.derich.shopauser.R
import com.derich.shopauser.databinding.FacilityItemBinding
import com.derich.shopauser.model.Facilities
import com.derich.shopauser.model.Services
import com.derich.shopauser.ui.common.DataBoundListAdapter
import com.derich.shopauser.ui.home.ServicesAdapter

class FacilitiesAdapter (
    private val mListener: FacilityClickListener,
    private val onClick: ((Facilities) -> Unit)? = null
    ) : DataBoundListAdapter<Facilities, FacilityItemBinding>(
    diffCallback = object : DiffUtil.ItemCallback<Facilities>() {
        override fun areItemsTheSame(oldItem: Facilities, newItem: Facilities): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Facilities, newItem: Facilities): Boolean {
            return oldItem.equals(newItem)
        }
    }
    ) {
        override fun createBinding(parent: ViewGroup): FacilityItemBinding {
            return DataBindingUtil.inflate<FacilityItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.facility_item,
                parent, false
            ).apply {
                this.root.setOnClickListener {
                    this.facility?.let { onClick?.invoke(it) }
                    mListener.onFacilityItemClicked(facility!!)
                }
            }
        }

        override fun bind(binding: FacilityItemBinding, item: Facilities) {
            binding.facility = item
        }
    interface FacilityClickListener {
        fun onFacilityItemClicked(facility: Facilities)
    }
    }