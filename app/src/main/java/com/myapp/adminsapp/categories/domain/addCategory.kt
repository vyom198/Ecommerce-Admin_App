package com.myapp.adminsapp.categories.domain

import android.net.Uri
import com.myapp.adminsapp.addproduct.domain.RealtimeProduct
import com.myapp.adminsapp.categories.data.Category

import com.myapp.adminsapp.core.common.ResultState

interface addCategoryRepo {

suspend fun insert (item: Category) : ResultState<Boolean>
suspend fun addImagesToFirebaseStorage(imageUri: Uri): ResultState<Uri>

}