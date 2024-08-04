package com.myapp.adminsapp.categories.di

import com.myapp.adminsapp.addproduct.domain.AddProductUsecase
import com.myapp.adminsapp.addproduct.domain.addProductRepo
import com.myapp.adminsapp.addproduct.presentation.AddproductViewmodel
import com.myapp.adminsapp.categories.domain.addCategoryRepo
import com.myapp.adminsapp.categories.presentation.CategoryViewmodel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton
//
//@Module
//@InstallIn(ViewModelComponent::class)
//object CategoryDomainModule {
//
//    @Provides
//    fun provideAddproductrepo(repo : addCategoryRepo) :CategoryViewmodel{
//        return CategoryViewmodel(repo)
//    }
//}