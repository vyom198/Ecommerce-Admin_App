package com.myapp.adminsapp.core.dataprovider.data

import android.net.Uri
import android.util.Log
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.myapp.adminsapp.addproduct.data.RealtimeProduct
import com.myapp.adminsapp.core.common.ResultState
import com.myapp.adminsapp.core.dataprovider.di.IoDispatcher
import com.myapp.adminsapp.core.dataprovider.domain.FirebaseRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import java.util.UUID
import javax.inject.Inject

class FireBaseRepoImpl  @Inject constructor(
        private val db: FirebaseFirestore,
        private  val storagedb: FirebaseStorage,
        @IoDispatcher private val ioDispatcher: CoroutineDispatcher
    ):FirebaseRepo{




        override suspend fun insert(item: RealtimeProduct.Product): ResultState<Any> {
            return try {
                withContext(ioDispatcher) {

                    val addTaskTimeout = withTimeoutOrNull(10000L) {
                        db.collection("AllProduct")
                            .add(item).addOnCompleteListener {
                                db.collection(item.productCategory.toString()).add(item)
                            }
                    }

                    if (addTaskTimeout == null) {
                        Log.d("ERROR: ", "check internet connection")
                        ResultState.Failure(IllegalStateException("check internet connection"))
                    }
                    ResultState.Success(Unit)
                }
            } catch (exception: Exception) {
                Log.d("ERROR: ", "$exception")

                ResultState.Failure(exception = exception)
            }
        }


        override suspend fun getItems(): Flow<ResultState<List<RealtimeProduct>>> = callbackFlow{
            trySend(ResultState.Loading)
            val listenerRegistration = db.collection("AllProducts")
                .addSnapshotListener { snapshot, exception ->
                    if (exception != null) {
                        trySend(ResultState.Failure(exception))
                        return@addSnapshotListener
                    }

                    snapshot?.let {
                        val items = it.documents.map { document ->
                            RealtimeProduct(
                                item = RealtimeProduct.Product(
                                    product = document["product"] as String?,
                                    price = document["price"] as Int?,
                                    productQuantity = document["productQuantity"] as Int?,
                                    productUnit = document["productUnit"] as String?,
                                    productStock = document["productStock"] as Int?,
                                    productCategory = document["productCategory"] as String?,
                                    productType = document["productType"] as String?,
                                    ItemCount = document["itemCount"] as Int?,
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

        override fun delete(key: String): Flow<ResultState<String>> = callbackFlow{
            trySend(ResultState.Loading)
            db.collection("AllProducts")
                .document(key)
                .delete()
                .addOnCompleteListener {
                    if(it.isSuccessful)
                        trySend(ResultState.Success("Deleted successfully.."))
                }.addOnFailureListener {
                    trySend(ResultState.Failure(it))
                }
            awaitClose {
                close()
            }
        }

        override fun update(item: RealtimeProduct): Flow<ResultState<String>> = callbackFlow{
            trySend(ResultState.Loading)
            val map = HashMap<String,Any>()
            map["product"] = item.item?.product!!
            map["price"] = item.item.price!!
            map["productCategory"] = item.item.productCategory!!
            map["productQuantity"] = item.item.productQuantity!!
            map["productType"]= item.item.productType!!
            map["productUnit"] = item.item.productUnit!!
            map["productStock"] = item.item.productStock!!
            map["itemCount"] = item.item.ItemCount!!
            map["productImageUris"] = item.item.productImageUris!!
            db.collection("AllProducts")
                .document(item.key!!)
                .update(map)
                .addOnCompleteListener {
                    if(it.isSuccessful)
                        trySend(ResultState.Success("Update successfully..."))
                }.addOnFailureListener {
                    trySend(ResultState.Failure(it))
                }
            awaitClose {
                close()
            }
        }

    override suspend fun addImagesToFirebaseStorage(imageUris: List<Uri>): ResultState<MutableList<Uri>> {
        val results = mutableListOf<Uri>()
        return try {
            imageUris.forEach {
                val imageref =
                    storagedb.reference.child("Images").child("images/${UUID.randomUUID()}")
                val uploadTask = imageref.putFile(it).await()
                Log.d("repo", uploadTask.toString())
                val downloadUrl = imageref.downloadUrl.await()
                Log.d("repo", downloadUrl.toString())
                results.add(downloadUrl)
            }
            ResultState.Success(data = results)
        } catch (e: FirebaseNetworkException) {
            ResultState.Failure(exception = e)
        } catch (e: Exception) {
            // Catch any other unexpected exceptions
            Log.e("ERROR", "Unexpected error: $e")
            ResultState.Failure(exception = e)
        }
    }


}

