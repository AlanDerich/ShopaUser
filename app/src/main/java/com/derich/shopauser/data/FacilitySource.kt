package com.derich.shopauser.data

import android.net.Uri
import com.derich.shopauser.model.Facilities

interface FacilitySource {
    suspend fun getFacilities(facilityCategoty: String): Result<List<Facilities>>
}