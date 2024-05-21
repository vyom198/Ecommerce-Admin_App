package com.myapp.adminsapp.addproduct.data

import android.net.Uri
import android.util.Log
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.myapp.adminsapp.core.common.ResultState
import com.myapp.adminsapp.core.dataprovider.di.IoDispatcher
import com.myapp.adminsapp.addproduct.domain.addProductRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import java.util.UUID
import javax.inject.Inject

class addProductRepoImpl  @Inject constructor(
        private val db: FirebaseFirestore,
        private  val storagedb: FirebaseStorage,
        @IoDispatcher private val ioDispatcher: CoroutineDispatcher
    ): addProductRepo {

    override suspend fun addImagesToFirebaseStorage(imageUris: List<Uri>): ResultState<MutableList<Uri>> {
        val results = mutableListOf<Uri>()

        return try {
            coroutineScope {
                val deferredResults = imageUris.map { uri ->
                    async {

                        val imageref = storagedb.reference.child("Images")
                            .child("images/${UUID.randomUUID()}")
                        val uploadTask = imageref.putFile(uri).await()
                        Log.d("repo", uploadTask.toString())
                        val downloadUrl = imageref.downloadUrl.await()
                        Log.d("repo", downloadUrl.toString())
                        downloadUrl
                    }
                }
                results.addAll(deferredResults.awaitAll())
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


        override suspend fun insert(item: RealtimeProduct.Product): ResultState<Boolean> {
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
                    ResultState.Success(true )
                }
            } catch (exception: Exception) {
                Log.d("ERROR: ", "$exception")

                ResultState.Failure(exception = exception)
            }
        }


//        override fun delete(key: String): Flow<ResultState<String>> = callbackFlow{
//            trySend(ResultState.Loading)
//            db.collection("AllProducts")
//                .document(key)
//                .delete()
//                .addOnCompleteListener {
//                    if(it.isSuccessful)
//                        trySend(ResultState.Success("Deleted successfully.."))
//                }.addOnFailureListener {
//                    trySend(ResultState.Failure(it))
//                }
//            awaitClose {
//                close()
//            }
//        }

//        override fun update(item: RealtimeProduct): Flow<ResultState<String>> = callbackFlow{
         // trySend(ResultState.Loading)
//            val map = HashMap<String,Any>()
//            map["product"] = item.item?.product!!
//            map["price"] = item.item.price!!
//            map["productCategory"] = item.item.productCategory!!
//            map["productQuantity"] = item.item.productQuantity!!
//            map["productType"]= item.item.productType!!
//            map["productUnit"] = item.item.productUnit!!
//            map["productStock"] = item.item.productStock!!
//            map["itemCount"] = item.item.ItemCount!!
//            map["productImageUris"] = item.item.productImageUris!!
//            db.collection("AllProducts")
//                .document(item.key!!)
//                .update(map)
//                .addOnCompleteListener {
//                    if(it.isSuccessful)
//                        trySend(ResultState.Success("Update successfully..."))
//                }.addOnFailureListener {
//                    trySend(ResultState.Failure(it))
//                }
//            awaitClose {
//                close()
//            }
//        }



}

