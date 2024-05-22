package com.myapp.adminsapp.addproduct.presentation

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.adminsapp.addproduct.domain.RealtimeProduct
import com.myapp.adminsapp.addproduct.domain.AddProductUsecase
import com.myapp.adminsapp.core.common.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
@HiltViewModel
class AddproductViewmodel @Inject constructor(
    private val usecase: AddProductUsecase
):ViewModel() {

    private val _Imgstate = MutableStateFlow(ImageUploadState())
    val Imgstate = _Imgstate.asStateFlow()


    private var addImagesToStorageResponse by mutableStateOf<ResultState<List<Uri>>>(ResultState.Loading)
    var imageUrl = mutableListOf<String>()

    var name = mutableStateOf("")
      private set

    var quantity =  mutableStateOf("")
        private set

    var unit =  mutableStateOf("")
        private set

    var price = mutableStateOf("")
        private set

    var productCategory =  mutableStateOf("")
        private set

    var noOfStock =  mutableStateOf("")
        private set

    var productType =  mutableStateOf("")
        private set
    var selectedImageuris = mutableStateOf<List<Uri>>(emptyList())
        private set


    fun resetFields() {
        viewModelScope.launch {
            name.value = ""
            quantity.value = ""
            unit.value = ""
            price.value = ""
            productCategory.value = ""
            noOfStock.value = ""
            productType.value = ""
            selectedImageuris.value = emptyList()
        }
    }

    suspend fun updateImageUrl() {
        withContext(Dispatchers.Main) {

            when (addImagesToStorageResponse) {
                is  ResultState.Success -> {
                    (addImagesToStorageResponse as ResultState.Success<List<Uri>>).data.forEach {
                        val urltoString = it.toString()
                        imageUrl.add(urltoString)
                        Log.d("viewmodel", imageUrl.toString())
                        _Imgstate.update {
                            it.copy(
                                succes = "Image is uploaded to server",
                                error = null
                            )
                        }

                    }
                }

                is ResultState.Failure -> {
                    _Imgstate.update {
                        it.copy(
                           error = "error Occured",
                            succes = null,
                        )
                    }

                }

                 is ResultState.Loading -> {
                    _Imgstate.update {
                        it.copy(
                            error =null,
                            succes = null,
                            isLoading = true
                        )
                    }
                }
            }
        }

    }


    fun addImageToStorage(imageUri: List<Uri>) = viewModelScope.launch {
        addImagesToStorageResponse = ResultState.Loading
        addImagesToStorageResponse = usecase.addImagesToFirebaseStorage(imageUri)
        Log.d("viewmodel",addImagesToStorageResponse.toString())
        updateImageUrl()
    }


    fun insertItem (
        item: RealtimeProduct.Product
    ){
        viewModelScope.launch {
            usecase.insertProduct(item)
        }
    }


//    private val _updateRes: MutableStateFlow<RealtimeModelResponse> = MutableStateFlow(
//        RealtimeModelResponse(
//            item = RealtimeModelResponse.RealtimeItems(),
//        )
//    )
//    val updateRes  = _updateRes.asStateFlow()
//
//    fun setData(data:RealtimeModelResponse){
//        _updateRes.value = data
//    }



}


data class ImageUploadState(
    val succes : String ? = null,
    val isLoading: Boolean =false,
    val error : String ? = "error",
)
