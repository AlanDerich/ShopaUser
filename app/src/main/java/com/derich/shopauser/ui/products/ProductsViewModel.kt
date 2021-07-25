package com.derich.shopauser.ui.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.derich.shopauser.data.ProductsRepository
import com.derich.shopauser.data.Result
import com.derich.shopauser.model.Products
import kotlinx.coroutines.Dispatchers

class ProductsViewModel : ViewModel() {

    private val coroutineContext = viewModelScope.coroutineContext + Dispatchers.IO
    private var repository: ProductsRepository = ProductsRepository()

    fun loadProducts(productsFacility: String): LiveData<Result<List<Products>>> = liveData(coroutineContext) {
        // start with loading.
        emit(Result.Loading)

        val result = repository.getProducts(productsFacility)
        // if list is empty, consider it as error to be displayed.
        if (result is Result.Success && result.data.isEmpty())
            emit(Result.Error(Exception("There are no products at the moment.\n Wait for it.")))
        else
            emit(result)
    }
}