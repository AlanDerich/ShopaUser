package com.derich.shopauser.data

import android.net.Uri
import com.derich.shopauser.model.Facilities
import com.derich.shopauser.model.Products

interface ProductsSource {
    suspend fun getProducts(productFacility: String): Result<List<Products>>
}