/**
 *                           MIT License
 *
 *                 Copyright (c) 2019 Amr Elghobary
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.derich.shopauser.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.derich.shopauser.R
import com.derich.shopauser.databinding.DealItemBinding
import com.derich.shopauser.model.Services
import com.derich.shopauser.ui.common.DataBoundListAdapter

class ServicesAdapter(
    private val mListener: ServiceClickListener,
    private val onClick: ((Services) -> Unit)? = null
) : DataBoundListAdapter<Services, DealItemBinding>(
    diffCallback = object : DiffUtil.ItemCallback<Services>() {
        override fun areItemsTheSame(oldItem: Services, newItem: Services): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Services, newItem: Services): Boolean {
            return oldItem.equals(newItem)
        }
    }
) {
    override fun createBinding(parent: ViewGroup): DealItemBinding {
        return DataBindingUtil.inflate<DealItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.deal_item,
            parent, false
        ).apply {
            this.root.setOnClickListener {
                this.deal?.let { onClick?.invoke(it) }
//                Toast.makeText(parent.context,"clicked",Toast.LENGTH_SHORT).show()
                mListener.onServiceItemClicked(deal!!)
            }
        }
    }

    override fun bind(binding: DealItemBinding, item: Services) {
        binding.deal = item
    }
    interface ServiceClickListener {
        fun onServiceItemClicked(service: Services)
    }
}