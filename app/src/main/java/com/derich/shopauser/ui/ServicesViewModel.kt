package com.derich.shopauser.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.derich.shopauser.data.ServicesRepository
import com.derich.shopauser.model.Services
import kotlinx.coroutines.Dispatchers
import com.derich.shopauser.data.Result

class ServicesViewModel : ViewModel() {

    private val coroutineContext = viewModelScope.coroutineContext + Dispatchers.IO
    private var repository: ServicesRepository = ServicesRepository()

    fun loadServices(): LiveData<Result<List<Services>>> = liveData(coroutineContext) {
        // start with loading.
        emit(Result.Loading)

        val result = repository.getServices()
        // if list is empty, consider it as error to be displayed.
        if (result is Result.Success && result.data.isEmpty())
            emit(Result.Error(Exception("There is no deals at the moment.\n Wait for it.")))
        else
            emit(result)
    }
}