package com.myapp.adminsapp.auth.data

import android.annotation.SuppressLint
import android.app.Activity
import android.service.autofill.UserData
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.myapp.adminsapp.auth.domain.PhoneAuthRepo
import com.myapp.adminsapp.core.common.ResultState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class PhoneAuthRepoImpl   @Inject constructor(
    private val authdb: FirebaseAuth,
    private val firebaseDb : FirebaseFirestore
) : PhoneAuthRepo {

    private lateinit var onVerificationCode:String


    override fun createUserWithPhone(phone: String,activity: Activity): Flow<ResultState<String>> =  callbackFlow{
        trySend(ResultState.Loading)

        val onVerificationCallback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {

            }

            override fun onVerificationFailed(p0: FirebaseException) {
                trySend(ResultState.Failure(p0))
            }

            override fun onCodeSent(verificationCode: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(verificationCode, p1)
                trySend(ResultState.Success("OTP Sent Successfully"))
                onVerificationCode = verificationCode
            }

        }

        val options = PhoneAuthOptions.newBuilder(authdb)
            .setPhoneNumber("+91$phone")
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(onVerificationCallback)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
        awaitClose{
            close()
        }
    }


    @SuppressLint("SuspiciousIndentation")
    override fun signWithCredential(otp: String): Flow<ResultState<SignInResult>>  = callbackFlow{
        trySend(ResultState.Loading)
        val credential = PhoneAuthProvider.getCredential(onVerificationCode,otp)
            authdb.signInWithCredential(credential)
            .addOnCompleteListener {
                if(it.isSuccessful){
                 val user = it.result.user
                 val result =   SignInResult(
                        data = user?.run {
                            Admin(
                                phone = phoneNumber,
                            )
                        },
                        errorMessage = null
                    )
                    trySend(ResultState.Success(result))
                }
            }.addOnFailureListener {
                trySend(ResultState.Failure(it))
            }
        awaitClose {
            close()
        }
    }

    override fun getSignInUser() = authdb.currentUser?.run {
         Admin(phone = phoneNumber)
    }


}

