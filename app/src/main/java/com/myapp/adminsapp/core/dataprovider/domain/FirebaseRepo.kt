package com.myapp.adminsapp.core.dataprovider.domain

import android.net.Uri
import com.myapp.adminsapp.core.common.ResultState
import com.myapp.adminsapp.core.dataprovider.data.RealtimeModelResponse
import kotlinx.coroutines.flow.Flow

interface FirebaseRepo {

        suspend fun insert(
            item: RealtimeModelResponse.Product
        ) : ResultState<Any>
        suspend fun addImageToFirebaseStorage(imageUri: Uri): ResultState<Uri>
        suspend fun getItems() : Flow<ResultState<List<RealtimeModelResponse>>>

        fun delete(key:String) : Flow<ResultState<Any>>

        fun update(
            item:RealtimeModelResponse
        ) : Flow<ResultState<Any>>

}