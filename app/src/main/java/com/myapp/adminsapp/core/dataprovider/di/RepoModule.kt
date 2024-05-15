package com.myapp.adminsapp.core.dataprovider.di

import com.myapp.adminsapp.core.dataprovider.data.FireBaseRepoImpl
import com.myapp.adminsapp.core.dataprovider.domain.FirebaseRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent ::class)
abstract class RepoModule {
    @Binds
    abstract fun providesRealtimeRepository(
        repo: FireBaseRepoImpl
    ):FirebaseRepo

}