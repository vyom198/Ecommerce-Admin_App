package com.myapp.adminsapp.allProducts.data

import com.google.firebase.firestore.FirebaseFirestore
import com.myapp.adminsapp.allProducts.domain.DomainProduct
import com.myapp.adminsapp.allProducts.domain.allProductsRepo
import com.myapp.adminsapp.core.common.ResultState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class allProductRepoImpl @Inject constructor(
    private val firedb : FirebaseFirestore,

) : allProductsRepo{
    override suspend fun getAllProducts(): Flow<ResultState<List<DomainProduct>>> =callbackFlow{
        trySend(ResultState.Loading)
        val listenerRegistration = firedb.collection("AllProducts")
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    trySend(ResultState.Failure(exception))
                    return@addSnapshotListener
                }

                snapshot?.let {
                    val items = it.documents.map { document ->
                      DomainProduct(
                            item = DomainProduct.Product(
                                product = document["product"] as String?,
                                price = document["price"] as Int?,
                                productQuantity = document["productQuantity"] as Int?,
                                productUnit = document["productUnit"] as String?,
                                productCategory = document["productCategory"] as String?,
                                productType = document["productType"] as String?,
                                productImageUris = document["productImageUris"] as List<String>?

                            ),
                            key = document.id
                        )
                    }
                    trySend(ResultState.Success(items))
                }
            }

        awaitClose {
            listenerRegistration.remove()
            close()
        }
    }




}