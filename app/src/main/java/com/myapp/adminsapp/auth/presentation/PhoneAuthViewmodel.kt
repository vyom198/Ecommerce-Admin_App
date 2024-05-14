package com.myapp.adminsapp.auth.presentation

import android.app.Activity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.adminsapp.auth.domain.PhoneAuthRepo
import com.myapp.adminsapp.core.common.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhoneAuthViewModel @Inject constructor(
    private val repo:PhoneAuthRepo
) : ViewModel() {

    var  mobileno by mutableStateOf("")
    var otp by mutableStateOf("")

    fun createUserWithPhone(
        mobile:String,
        activity: Activity
    ) = repo.createUserWithPhone(mobile,activity)


    fun signInWithCredential(
        code:String
    ) = repo.signWithCredential(code)

}