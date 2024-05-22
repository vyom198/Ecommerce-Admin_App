package com.myapp.adminsapp.addproduct.di

import com.myapp.adminsapp.addproduct.domain.AddProductUsecase
import com.myapp.adminsapp.addproduct.domain.addProductRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {

    @Provides
    fun provideAddproductUsecase (repo : addProductRepo) :AddProductUsecase{
        return AddProductUsecase(repo)
    }
}