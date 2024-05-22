package com.myapp.adminsapp.addproduct.domain

import android.net.Uri
import com.myapp.adminsapp.core.common.ResultState

interface addProductRepo {

        suspend fun insert(
            item: RealtimeProduct.Product
        ) : ResultState<Boolean>
        suspend fun addImagesToFirebaseStorage(imageUris: List<Uri>): ResultState<MutableList<Uri>>

}