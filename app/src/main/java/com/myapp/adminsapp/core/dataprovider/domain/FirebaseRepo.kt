package com.myapp.adminsapp.core.dataprovider.domain

import android.net.Uri
import com.myapp.adminsapp.addproduct.data.RealtimeProduct
import com.myapp.adminsapp.core.common.ResultState
import kotlinx.coroutines.flow.Flow

interface FirebaseRepo {

        suspend fun insert(
            item: RealtimeProduct.Product
        ) : ResultState<Any>
        suspend fun addImagesToFirebaseStorage(imageUris: List<Uri>): ResultState<MutableList<Uri>>
        suspend fun getItems() : Flow<ResultState<List<RealtimeProduct>>>

        fun delete(key:String) : Flow<ResultState<Any>>

        fun update(
            item:RealtimeProduct
        ) : Flow<ResultState<Any>>


}