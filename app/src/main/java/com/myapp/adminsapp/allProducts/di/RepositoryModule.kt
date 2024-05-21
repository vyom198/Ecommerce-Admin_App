package com.myapp.adminsapp.allProducts.di

import com.myapp.adminsapp.allProducts.data.allProductRepoImpl
import com.myapp.adminsapp.allProducts.domain.allProductsRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent ::class)
abstract class  allproductRepoModule {
    @Binds
    abstract fun providesAllProduct(
        repo: allProductRepoImpl
    ):allProductsRepo

}