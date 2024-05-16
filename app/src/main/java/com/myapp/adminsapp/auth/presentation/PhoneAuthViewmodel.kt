package com.myapp.adminsapp.auth.presentation

import android.app.Activity
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.adminsapp.auth.data.SignInResult
import com.myapp.adminsapp.auth.domain.PhoneAuthRepo
import com.myapp.adminsapp.core.common.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhoneAuthViewModel @Inject constructor(
    private val repo:PhoneAuthRepo
) : ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun onSignInResult(result: SignInResult) {
        _state.update { it.copy(
            isSignInSuccessful = result.data != null,

            signInError = result.errorMessage
        )
        }
        Log.d("signInResultviewmodel", _state.value.toString())
    }
    fun getSignInUser() = repo.getSignInUser()

    fun resetState() {
        _state.update { SignInState() }
    }
    fun createUserWithPhone(
        mobile:String,
        activity: Activity
    ) = repo.createUserWithPhone(mobile,activity)


    fun signInWithCredential(
        code:String
    ) = repo.signWithCredential(code)

}