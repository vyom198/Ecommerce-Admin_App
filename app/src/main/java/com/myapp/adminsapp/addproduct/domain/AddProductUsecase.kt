package com.myapp.adminsapp.addproduct.domain

import android.net.Uri
import com.myapp.adminsapp.addproduct.data.RealtimeProduct
import com.myapp.adminsapp.core.common.ResultState

interface AddProductUsecase {

        suspend fun addImagesToFirebaseStorage(imageUris: List<Uri>): ResultState<MutableList<Uri>>
        suspend fun insertProduct(item: RealtimeProduct.Product): ResultState<Boolean>

}