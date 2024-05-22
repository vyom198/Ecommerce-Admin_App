package com.myapp.adminsapp.addproduct.data.repository

import android.net.Uri
import android.util.Log
import com.myapp.adminsapp.addproduct.data.RealtimeProduct
import com.myapp.adminsapp.addproduct.domain.addProductRepo
import com.myapp.adminsapp.core.common.ResultState


class FakeAddProductRepo :addProductRepo {
    private val products = mutableListOf<RealtimeProduct.Product>()
    private val imageStorage = mutableMapOf<Uri, Uri>()
    override suspend fun insert(item: RealtimeProduct.Product): ResultState<Boolean> {
        return try {
            products.add(item)
            ResultState.Success(true)
        } catch (exception: Exception) {
            ResultState.Failure(exception)
        }
    }

    override suspend fun addImagesToFirebaseStorage(imageUris: List<Uri>): ResultState<MutableList<Uri>> {
        return try {
            val uploadedUris = imageUris.map { uri ->
                val uploadedUri = Uri.parse("fake://storage/${uri.lastPathSegment}")
                Log.d("test",uri.lastPathSegment.toString())
                imageStorage[uri] = uploadedUri
                uploadedUri
            }.toMutableList()
            ResultState.Success(uploadedUris)
        } catch (exception: Exception) {
            ResultState.Failure(exception)
        }
    }
}