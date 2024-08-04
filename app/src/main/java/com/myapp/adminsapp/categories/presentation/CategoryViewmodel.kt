package com.myapp.adminsapp.categories.presentation

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.adminsapp.categories.data.Category
import com.myapp.adminsapp.categories.domain.addCategoryRepo
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
class CategoryViewmodel @Inject constructor(
    private val repo : addCategoryRepo

): ViewModel() { 
    private val _Imgstate = MutableStateFlow(CategoryImageUploadState())
    val Imgstate = _Imgstate.asStateFlow()
    private var addImagesToStorageResponse by mutableStateOf<ResultState<Uri>>(ResultState.Loading)
    var imageUrl = mutableStateOf("")

    var productCategory =  mutableStateOf("")
        private set

    var productType =  mutableStateOf("")
        private set
    var selectedImageuri = mutableStateOf("".toUri())
        private set


    fun resetFields() {
        viewModelScope.launch {
            productCategory.value = ""
            productType.value = ""
            selectedImageuri.value = "".toUri()
        }
    }

    suspend fun updateImageUrl() {
        withContext(Dispatchers.Main) {

            when (addImagesToStorageResponse) {
                is  ResultState.Success -> {
                    (addImagesToStorageResponse as ResultState.Success<Uri>).data.let {
                        imageUrl.value = it.toString()
                        Log.d("viewmodel", imageUrl.toString())
                        _Imgstate.update {
                            it.copy(
                                success = "Image is uploaded to server",
                                error = null
                            )
                        }

                    }
                }

                is ResultState.Failure -> {
                    _Imgstate.update {
                        it.copy(
                            error = "Error Occured",
                            success = null,
                        )
                    }

                }

                is ResultState.Loading -> {
                    _Imgstate.update {
                        it.copy(
                            error =null,
                            success = null,
                            isLoading = true
                        )
                    }
                }
            }
        }

    }


    fun addImageToStorage(imageUri: Uri) = viewModelScope.launch {
        addImagesToStorageResponse = ResultState.Loading
        addImagesToStorageResponse = repo.addImagesToFirebaseStorage(imageUri)
        Log.d("viewmodel",addImagesToStorageResponse.toString())
        updateImageUrl()
    }


    fun insertItem (
        item: Category
    ){
        viewModelScope.launch {
            repo.insert(item)
        }
    }
}

data class CategoryImageUploadState(
    val success : String ? = null,
    val isLoading: Boolean =false,
    val error : String ? = "error",
)
