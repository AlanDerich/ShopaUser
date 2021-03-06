/**
 *                           MIT License
 *
 *                 Copyright (c) 2019 Amr Elghobary
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.derich.shopauser.util

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.derich.shopauser.data.Result
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import timber.log.Timber
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AuthManager(
    private val activity: Activity,
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) {

    val isUserLoggedIn: Boolean
        get() {
            return auth.currentUser != null
        }

    // Choose authentication providers
    private val providers = arrayListOf(
        AuthUI.IdpConfig.EmailBuilder().build(),
        AuthUI.IdpConfig.GoogleBuilder().build()
    )

    /**
     * This method launches UI to auth user using their preferred provider like Email, Google.
     *
     * Remember to check if user is already logged in or not, using [AuthManager.isUserLoggedIn]
     * to avoid any extra work.
     *
     * This method uses [Activity.startActivityForResult] to launch authentication. Consequently,
     * you need to receive the response in [Activity.onActivityResult]. To handle authentication
     * call [AuthManager.handleAuthentication] method after you override the method
     * [Activity.onActivityResult] in your activity (Unfortunately, there is no other way to handle
     * [Activity.onActivityResult] outside activity).
     */
    fun authUser() {
        activity.startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN
        )
    }

    /**
     * This method should be called inside overridden [Activity.onActivityResult], to handle
     * authentication result.
     *
     * @param action function has two parameters:
     *          1. isSuccessful: if authentication is successful.
     *          2. exception: Otherwise, it returns the Exception for failure reason.
     */
    fun handleAuthentication(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        action: (isSuccessful: Boolean, exception: Exception?) -> Unit
    ) {
        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)
            Timber.d("Authentication Response: %s", response?.toString())

            // Successfully signed in
            if (resultCode == Activity.RESULT_OK) {
                action.invoke(true, null)
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                response?.error?.let {
                    action.invoke(false, it)
                }
            }
        }
    }

    fun userEmail(): String? {
        return auth.currentUser?.email
    }

    fun signOut(): LiveData<Result<Boolean>> = liveData(Dispatchers.IO) {
        emit(startSignOut())
    }

    private suspend fun startSignOut(): Result<Boolean> = suspendCoroutine { cont ->
        AuthUI.getInstance().signOut(activity)
            .addOnFailureListener {
                cont.resume(Result.Error(it))
            }.addOnSuccessListener {
                cont.resume(Result.Success(true))
            }
    }

    companion object {
        private const val RC_SIGN_IN = 123
    }
}