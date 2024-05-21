package com.myapp.adminsapp.allProducts.domain

import com.myapp.adminsapp.core.common.ResultState
import kotlinx.coroutines.flow.Flow

interface allProductsRepo {

    suspend fun getAllProducts () : Flow<ResultState<List<DomainProduct>>>
}