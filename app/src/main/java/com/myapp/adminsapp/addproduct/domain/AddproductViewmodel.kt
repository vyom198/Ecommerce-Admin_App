package com.myapp.adminsapp.addproduct.domain

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.adminsapp.addproduct.data.RealtimeProduct
import com.myapp.adminsapp.core.common.ResultState
import com.myapp.adminsapp.core.dataprovider.domain.FirebaseRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class AddproductViewmodel @Inject constructor(
    private val repo : FirebaseRepo
):ViewModel() {
//    private val _state = MutableStateFlow(ProductState())
//    val state = _state.asStateFlow()
    private val _Imgstate = MutableStateFlow(ImageUploadState())
    val Imgstate = _Imgstate.asStateFlow()

    private var addImagesToStorageResponse by mutableStateOf<ResultState<List<Uri>>>(ResultState.Loading)
    var imageUrl   = mutableListOf<String>()


    fun updateImageUrl() {
        viewModelScope.launch {
            when (addImagesToStorageResponse) {
                is ResultState.Success -> {
                   (addImagesToStorageResponse as ResultState.Success<List<Uri>>).data.forEach {
                       val urltoString = it.toString()
                       imageUrl.add(urltoString)
                       Log.d("viewmodel",imageUrl.toString())
                       _Imgstate.update {
                           it.copy(
                               succes = "Image is uploaded to server",
                               isLoading = false,
                               error = null
                           )
                       }
                   }
                }
                is ResultState.Failure -> {
                    _Imgstate.value.copy(
                        error = "error Occured",
                        succes = null ,
                        isLoading = false
                    )
                }

                ResultState.Loading -> {
                    _Imgstate.value.copy(
                        isLoading = true,
                        error = null,
                        succes = null
                    )
                }
            }
        }

    }
    fun addImageToStorage(imageUri: List<Uri>) = viewModelScope.launch {
        addImagesToStorageResponse = ResultState.Loading
        addImagesToStorageResponse = repo.addImagesToFirebaseStorage(imageUri)
        Log.d("viewmodel",addImagesToStorageResponse.toString())
        updateImageUrl()
    }
//    fun loadStuff() {
//        viewModelScope.launch {
//            _isLoading.value = true
//            repo.getItems()
//            delay(3000)
//            _isLoading.value = false
//        }
//        Log.d("viewmodel","refresh")
//    }
    // ViewModel function to add image and insert item

    fun insertItem (
        item: RealtimeProduct.Product
    ){
        viewModelScope.launch {
            repo.insert(item)
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

//    init {
//
//        viewModelScope.launch {
//            repo.getItems().collect{
//                when(it){
//                    is ResultState.Success -> {
//                        _state.value = ProductState(
//                            item = it.data as List<RealtimeProduct>
//                        )
//                    }
//                    is ResultState.Failure->{
//                        _state.value = ProductState(
//                            error = it.toString()
//                        )
//                    }
//                    ResultState.Loading->{
//                        _state.value = ProductState(
//                            isLoading = true
//                        )
//                    }
//                }
//            }
//        }
//    }

//    fun delete(key:String) = repo.delete(key)
//    fun update(item:RealtimeModelResponse) = repo.update(item)


}


data class ProductState(
    val item: List<RealtimeProduct> = emptyList(),
    val error:String ? = "error",
    val isLoading:Boolean = false
)

data class ImageUploadState(
    val succes : String ? = null,
    val error : String ? = "error",
    val isLoading: Boolean = false
)
