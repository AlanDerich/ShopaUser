package com.derich.shopauser.model

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import kotlinx.android.parcel.Parcelize

@Parcelize
class Facilities(
    @DocumentId
    val id:String,
    val facilityName:String,
    val facilityStatus:Boolean,
    val facilityCategory: String,
    val facilityLocation: String,
    val facilityPic:String) : Parcelable {
        @Suppress("unused")
        constructor() : this("","",true,"","","")
    }