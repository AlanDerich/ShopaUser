package com.derich.shopauser.model

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import kotlinx.android.parcel.Parcelize

@Parcelize
class Services(
    @DocumentId
    val id:String,
    val serviceName:String,
    val servicePic:String) : Parcelable
{
    @Suppress("unused")
    constructor() : this("","","")
}