package com.myapp.adminsapp.addproduct.di

import com.myapp.adminsapp.addproduct.data.addProductRepoImpl
import com.myapp.adminsapp.addproduct.domain.addProductRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent ::class)
abstract class addProductrepoModule{
    @Binds
    abstract fun providesRealtimeRepository(
        repo: addProductRepoImpl
    ): addProductRepo

}