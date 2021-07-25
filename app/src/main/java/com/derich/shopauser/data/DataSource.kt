package com.derich.shopauser.data

import android.net.Uri
import com.derich.shopauser.model.Services

interface DataSource {

    suspend fun getServices(): Result<List<Services>>
}