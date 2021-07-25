package com.derich.shopauser.ui.facilities

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.derich.shopauser.data.FacilitiesRepository
import com.derich.shopauser.data.Result
import com.derich.shopauser.data.ServicesRepository
import com.derich.shopauser.model.Facilities
import com.derich.shopauser.model.Services
import kotlinx.coroutines.Dispatchers

class FacilitiesViewModel : ViewModel() {

    private val coroutineContext = viewModelScope.coroutineContext + Dispatchers.IO
    private var repository: FacilitiesRepository = FacilitiesRepository()

    fun loadFacilities(facilityCategory: String): LiveData<Result<List<Facilities>>> = liveData(coroutineContext) {
        // start with loading.
        emit(Result.Loading)

        val result = repository.getFacilities(facilityCategory)
        // if list is empty, consider it as error to be displayed.
        if (result is Result.Success && result.data.isEmpty())
            emit(Result.Error(Exception("There is no deals at the moment.\n Wait for it.")))
        else
            emit(result)
    }
}