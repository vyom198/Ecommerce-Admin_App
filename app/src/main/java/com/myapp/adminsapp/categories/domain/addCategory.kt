package com.myapp.adminsapp.categories.domain

import android.net.Uri
import com.myapp.adminsapp.addproduct.domain.RealtimeProduct
import com.myapp.adminsapp.allProducts.domain.DomainProduct
import com.myapp.adminsapp.categories.data.Category
import com.myapp.adminsapp.categories.data.RealtimeCategory

import com.myapp.adminsapp.core.common.ResultState
import kotlinx.coroutines.flow.Flow

interface addCategoryRepo {

suspend fun insert (item: Category) : ResultState<Boolean>
suspend fun addImagesToFirebaseStorage(imageUri: Uri): ResultState<Uri>
suspend fun getAllCategories () : Flow<ResultState<List<RealtimeCategory>>>
}