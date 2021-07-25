package com.derich.shopauser.model

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import kotlinx.android.parcel.Parcelize

@Parcelize
class Products(
    @DocumentId
    val id:String,
    val productName:String,
    val productPrice:Double,
    val productDetails:String,
    val productFacilityName:String,
    val productPic:String) : Parcelable
{
    @Suppress("unused")
    constructor() : this("","", 0.0,"","","")
}