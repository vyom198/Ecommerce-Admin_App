package com.myapp.adminsapp.allProducts.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.adminsapp.allProducts.domain.DomainProduct
import com.myapp.adminsapp.allProducts.domain.allProductsRepo
import com.myapp.adminsapp.core.common.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllProductViewmodel @Inject constructor(
    private  val repo : allProductsRepo
):ViewModel() {
    private  val _state = MutableStateFlow(AllProductsState())
     val state = _state.asStateFlow()

    init {
      getAllProducts()
    }
    private fun getAllProducts(){
         viewModelScope.launch {
             repo.getAllProducts().collectLatest { result ->
                 when (result) {

                     is ResultState.Success -> {
                         _state.update {
                             it.copy(
                                 item = result.data
                             )
                         }
                     }
                         is ResultState.Failure -> {
                             _state.update {
                                 it.copy(
                                     error = it.error
                                 )
                             }
                         }
                         is ResultState.Loading -> {
                             _state.update {
                                 it.copy(
                                     isLoading = true
                                 )
                             }
                         }
                     }


                 }
             }
         }

     }



data class AllProductsState(
    val item : List<DomainProduct> = emptyList(),
    val isLoading : Boolean = false,
    val error : String? = null
)