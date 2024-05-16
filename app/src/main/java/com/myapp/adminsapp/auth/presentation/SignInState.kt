package com.myapp.adminsapp.auth.presentation


data class SignInState (
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)