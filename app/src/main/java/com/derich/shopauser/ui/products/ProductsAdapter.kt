package com.derich.shopauser.ui.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.derich.shopauser.R
import com.derich.shopauser.databinding.ProductItemBinding
import com.derich.shopauser.model.Facilities
import com.derich.shopauser.model.Products
import com.derich.shopauser.ui.common.DataBoundListAdapter
import com.derich.shopauser.ui.facilities.FacilitiesAdapter

class ProductsAdapter (
    private val mListener: ProductClickListener,
    private val onClick: ((Products) -> Unit)? = null
    ) : DataBoundListAdapter<Products, ProductItemBinding>(
    diffCallback = object : DiffUtil.ItemCallback<Products>() {
        override fun areItemsTheSame(oldItem: Products, newItem: Products): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Products, newItem: Products): Boolean {
            return oldItem.equals(newItem)
        }
    }
    ) {
        override fun createBinding(parent: ViewGroup): ProductItemBinding {
            return DataBindingUtil.inflate<ProductItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.product_item,
                parent, false
            ).apply {
                this.root.setOnClickListener {
                    this.product?.let { onClick?.invoke(it) }
                    mListener.onProductItemClicked(product!!)
                }
            }
        }

        override fun bind(binding: ProductItemBinding, item: Products) {
            binding.product = item
        }
    interface ProductClickListener {
        fun onProductItemClicked(product: Products)
    }
    }