package com.myapp.adminsapp.categories.data

import android.net.Uri
import android.util.Log
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.myapp.adminsapp.addproduct.domain.RealtimeProduct
import com.myapp.adminsapp.addproduct.domain.addProductRepo
import com.myapp.adminsapp.categories.domain.addCategoryRepo
import com.myapp.adminsapp.core.common.ResultState
import com.myapp.adminsapp.core.dataprovider.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import java.util.UUID
import javax.inject.Inject

class addCategoryRepoIMpl @Inject constructor(
    private val db: FirebaseFirestore,
    private  val storagedb: FirebaseStorage,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) :addCategoryRepo {
    override suspend fun insert(item: Category): ResultState<Boolean> {
        return try {
            withContext(ioDispatcher) {

                val addTaskTimeout = withTimeoutOrNull(10000L) {
                    db.collection("AllCategories")
                        .add(item).addOnCompleteListener {

                                db.collection(item.CategoryType).add(item)
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

    override suspend fun addImagesToFirebaseStorage(imageUri: Uri): ResultState<Uri> {
            return try {
                val deferredResult = imageUri.let { uri ->
                    coroutineScope {
                        async {

                            val imageref = storagedb.reference.child("CategoriesImages")
                                .child("images/${UUID.randomUUID()}")
                            val uploadTask = imageref.putFile(uri).await()
                            Log.d("repo", uploadTask.toString())
                            val downloadUrl = imageref.downloadUrl.await()
                            Log.d("repo", downloadUrl.toString())
                            downloadUrl
                        }
                    }
                    }

                ResultState.Success(data = deferredResult.await())
            } catch (e: FirebaseNetworkException) {
                ResultState.Failure(exception = e)
            } catch (e: Exception) {
                // Catch any other unexpected exceptions
                Log.e("ERROR", "Unexpected error: $e")
                ResultState.Failure(exception = e)
            }


        }



}