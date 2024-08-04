package com.myapp.adminsapp.categories.di

import com.myapp.adminsapp.categories.data.addCategoryRepoIMpl
import com.myapp.adminsapp.categories.domain.addCategoryRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
abstract class addCategoryrepoModule{
    @Binds
    abstract fun providesRealtimeRepository(
        repo:  addCategoryRepoIMpl
    ): addCategoryRepo

}