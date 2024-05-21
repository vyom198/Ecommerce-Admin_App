package com.myapp.adminsapp.addproduct.domain

import android.net.Uri
import com.myapp.adminsapp.addproduct.data.RealtimeProduct
import com.myapp.adminsapp.core.common.ResultState
import kotlinx.coroutines.flow.Flow

interface addProductRepo {

        suspend fun insert(
            item: RealtimeProduct.Product
        ) : ResultState<Boolean>
        suspend fun addImagesToFirebaseStorage(imageUris: List<Uri>): ResultState<MutableList<Uri>>

}