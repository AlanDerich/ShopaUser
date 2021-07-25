package com.derich.shopauser.data

import android.net.Uri
import com.derich.shopauser.data.FacilitySource
import com.derich.shopauser.model.Facilities
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FacilitiesRepository : FacilitySource {
    private val db: FirebaseFirestore = Firebase.firestore
    private val storage = FirebaseStorage.getInstance().reference

    override suspend fun getFacilities(category: String): Result<List<Facilities>> =
        suspendCoroutine { cont ->
            db.collection("facilities")
                .whereEqualTo("facilityCategory",category)
                .get()
                .addOnSuccessListener {
                    try {
                        cont.resume(Result.Success(it.toObjects()))
                    } catch (e: Exception) {
                        cont.resume(Result.Error(e))
                    }
                }.addOnFailureListener {
                    cont.resume(Result.Error(it))
                }
//            db.collection("services").addSnapshotListener{
//                snapshot,e ->
//                if (e !=null){
//                    Log.w(TAG,"listen failed",e)
//                    return@addSnapshotListener
//                }
//                if (snapshot!=null){
//                    try {
//                        cont.resume(Result.Success(snapshot.toObjects()))
//                    } catch (e: Exception) {
//                        cont.resume(Result.Error(e))
//                    }
//                }
//            }
        }
}