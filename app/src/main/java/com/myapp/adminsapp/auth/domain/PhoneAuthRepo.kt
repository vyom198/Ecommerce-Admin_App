package com.myapp.adminsapp.auth.domain

import android.app.Activity
import com.myapp.adminsapp.auth.data.Admin
import com.myapp.adminsapp.core.common.ResultState
import kotlinx.coroutines.flow.Flow

interface PhoneAuthRepo {

    fun createUserWithPhone(
        phone:String,
        activity: Activity
    ) : Flow<ResultState<String>>

    fun signWithCredential(
        otp:String
    ): Flow<ResultState<String>>

}