package com.myapp.adminsapp.addproduct.presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Category
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.myapp.adminsapp.addproduct.domain.RealtimeProduct
import com.myapp.adminsapp.core.common.ProductCategory
import com.myapp.adminsapp.core.common.ProductType
import com.myapp.adminsapp.core.common.TestTags
import com.myapp.adminsapp.core.common.Unitlist
import com.myapp.adminsapp.core.common.showMsg
import com.myapp.adminsapp.core.composables.CommonDialog
import com.myapp.adminsapp.core.composables.ProductEditTxtField
import com.myapp.adminsapp.core.composables.TextfielDropDown
import com.myapp.adminsapp.core.composables.horizontalSpacer
import com.myapp.adminsapp.core.composables.verticalSpacer
import com.myapp.adminsapp.ui.theme.Saffron
import kotlinx.coroutines.runBlocking

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ResourceAsColor", "SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(
    viewmodel : AddproductViewmodel
) {
    val ImgUploadState by viewmodel.Imgstate.collectAsState()

    val name =  remember { viewmodel.name }
    val quantity = remember {
        viewmodel.quantity
    }
    val unit = remember {
        viewmodel.unit
    }
    val price  =remember {
        viewmodel.price
    }
    val productCategory  =remember {
        viewmodel.productCategory
    }
    val noOfStock  =remember {
        viewmodel.noOfStock
    }
    val productType  =remember {
        viewmodel.productType
    }
    var reset by remember {
        mutableStateOf(false)
    }
    val showDialog = viewmodel.Imgstate.value.isLoading

    val expandedValue = remember { mutableStateOf(false) }
    val expandedValue2 = remember { mutableStateOf(false) }
    val expandedValue3 = remember {mutableStateOf(false) }
    var selectedImageUris by remember { viewmodel.selectedImageuris }

    val isFormValid = name.value.isNotBlank() && quantity.value.isNotBlank() && unit.value.isNotBlank() &&
            price.value.isNotBlank() && productCategory.value.isNotBlank()&&
            productType.value.isNotBlank()

            val context = LocalContext.current
            Scaffold(modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(
                        title = { Text(text = "Add Product") },
                    )
                }) {


                LaunchedEffect(key1 = ImgUploadState){
                    Log.d("add product", showDialog.toString())
                    if (ImgUploadState.succes != null){
                        context.showMsg(msg = ImgUploadState.succes.toString())
                    }

                }
              runBlocking {
                  if (reset){
                      viewmodel.resetFields()
                  }
              }
              if (showDialog){
                  Log.d("dialog", showDialog.toString())
                  CommonDialog()
              }

                val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.PickMultipleVisualMedia(),
                    onResult = { uris ->
                        viewmodel.addImageToStorage(uris)
                        selectedImageUris = uris
                    }
                )


                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {

                            verticalSpacer()
                            Text(
                                text = "Please fill product details!",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = Saffron
                            )

                            verticalSpacer()
                            ProductEditTxtField(title = name, label = "Product name", height = 140.dp)
                            verticalSpacer(dp = 8.dp)
                            Row(
                                modifier = Modifier.width(340.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                ProductEditTxtField(
                                    title = quantity,
                                    label = "Quantity(kg,l)",
                                    width = 170.dp,
                                )
                                horizontalSpacer(8.dp)
                                TextfielDropDown(category = Unitlist, title = unit ,
                                    expanded = expandedValue ,width = 170.dp  , label = "",hint = "units")

                            }
                            verticalSpacer(dp = 8.dp)
                            Row(
                                modifier = Modifier.width(340.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                ProductEditTxtField(title = price, label = "price", width = 170.dp,)
                                horizontalSpacer(8.dp)
                                ProductEditTxtField(title = noOfStock, label = "No. of Stock", width = 170.dp,)
                            }

                            TextfielDropDown(category = ProductCategory, title =  productCategory, label ="product category" ,
                                expanded = expandedValue3 , leadingIcon = Icons.Default.Category)
                            TextfielDropDown(category = ProductType, title = productType, label ="product type" ,
                                expanded = expandedValue2,leadingIcon = Icons.Default.Category )

                            verticalSpacer()
                            Row(
                                modifier = Modifier.width(340.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Please Select some Images",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Saffron
                                )
                                horizontalSpacer()
                                Icon(
                                    imageVector = Icons.Default.AddPhotoAlternate,
                                    contentDescription = null,
                                    modifier = Modifier.clickable {
                                        multiplePhotoPickerLauncher.launch(
                                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                        )
                                    }
                                )
                            }

                            LazyRow(modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp),
                                horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically){
                                items(selectedImageUris){
                                    AsyncImage(model = it,
                                        contentDescription =null,
                                        modifier = Modifier
                                            .width(70.dp)
                                            .height(70.dp))
                                }


                            }


                            Button(onClick = {
                                viewmodel.insertItem(
                                    item = RealtimeProduct.Product(
                                        product = name.value ,
                                        price = price.value.toInt(),
                                        productCategory = productCategory.value,
                                        productQuantity = quantity.value.toInt(),
                                        productUnit = unit.value ,
                                        productStock = noOfStock.value.toInt(),
                                        productType = productType.value,
                                        productImageUris = viewmodel.imageUrl
                                    )
                                )
                                reset = true
                                context.showMsg("product saved")
                            },
                                modifier = Modifier
                                    .width(340.dp)
                                    .height(50.dp).testTag(TestTags.SAVEBUTTON),
                                colors = ButtonColors(
                                    containerColor = Saffron, contentColor = Color.White, disabledContainerColor = Color.Transparent,
                                    disabledContentColor = Color.Transparent),
                                enabled = isFormValid
                            ) {
                                Text(text = "Save Product")
                            }

                        }


                    }
}


@Preview(showBackground = true)
@Composable
fun AddProductScreenPreview() {
    //AddProductScreen(hiltViewModel())
}

