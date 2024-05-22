package com.myapp.adminsapp.addproduct.domain

import android.net.Uri
import com.myapp.adminsapp.addproduct.data.RealtimeProduct
import com.myapp.adminsapp.addproduct.data.repository.FakeAddProductRepo
import com.myapp.adminsapp.core.common.ResultState
import io.mockk.every
import io.mockk.mockk

import kotlinx.coroutines.runBlocking


import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest=Config.NONE)
class AddProductUsecaseImplTest {

    private lateinit var  addProductUsecase :AddProductUsecaseImpl
    private lateinit var fakeAddProductRepo: FakeAddProductRepo

    @Before
    fun setUp() {
        fakeAddProductRepo = FakeAddProductRepo()
        addProductUsecase = AddProductUsecaseImpl(fakeAddProductRepo)
    }



    @Test
    fun `upload image to firebase and get mutable list of uri` () = runBlocking {

        val imageUris = listOf((Uri.parse("file:///image1"))
            ,(Uri.parse("file:///image2")))
        val expectectResult = ResultState.Success(
            mutableListOf(
                Uri.parse("fake://storage/image1"),
                Uri.parse("fake://storage/image2")
            )
        )
        val result = addProductUsecase.addImagesToFirebaseStorage(imageUris)

        assertEquals(expectectResult,result)
    }

    @Test
    fun `insertProduct should return success`() = runBlocking {
        val product = RealtimeProduct.Product(
            product = "Test Product",
            price = 100,
            productCategory = "Test Category",
            productQuantity = 10,
            productUnit = "kg",
            productStock = 50,
            productType = "Test Type",
            productImageUris = mutableListOf("uri1", "uri2")
        )
        val expectedResult = ResultState.Success(true)

        val result = addProductUsecase.insertProduct(product)
        assertEquals(expectedResult,result)
    }



}