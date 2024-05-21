package com.myapp.adminsapp.addproduct.di

import com.myapp.adminsapp.addproduct.domain.AddProductUsecase
import com.myapp.adminsapp.addproduct.domain.AddProductUsecaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent ::class)
abstract class addProductusecaseModule{
    @Binds
    abstract fun providesRealtimeRepository(
        repo: AddProductUsecaseImpl
    ): AddProductUsecase

}