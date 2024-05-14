package com.myapp.adminsapp.auth.data.di

import com.myapp.adminsapp.auth.data.PhoneAuthRepoImpl
import com.myapp.adminsapp.auth.domain.PhoneAuthRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {


    @Binds
    abstract fun providesFirebaseAuthRepository(
        repo:PhoneAuthRepoImpl
    ):PhoneAuthRepo

}