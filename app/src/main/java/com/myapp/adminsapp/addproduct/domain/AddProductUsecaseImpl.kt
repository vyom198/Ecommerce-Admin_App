package com.myapp.adminsapp.addproduct.domain

import android.net.Uri
import com.myapp.adminsapp.addproduct.data.RealtimeProduct
import com.myapp.adminsapp.addproduct.data.addProductRepoImpl
import com.myapp.adminsapp.core.common.ResultState
import javax.inject.Inject

class AddProductUsecaseImpl @Inject constructor(
    private  val repo : addProductRepo
) : AddProductUsecase{
    override suspend fun addImagesToFirebaseStorage(imageUris: List<Uri>): ResultState<MutableList<Uri>> {
      return repo.addImagesToFirebaseStorage(imageUris)
    }

    override suspend fun insertProduct(item: RealtimeProduct.Product): ResultState<Boolean> {
        return try {
            repo.insert(item)
            ResultState.Success(true)
        } catch (exception: Exception) {
            ResultState.Failure(exception)
        }

    }
}