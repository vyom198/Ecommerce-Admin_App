package com.myapp.adminsapp.admin.presentation

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.myapp.adminsapp.R
import com.myapp.adminsapp.core.composables.ProductEditTxtField
import com.myapp.adminsapp.core.composables.horizontalSpacer
import com.myapp.adminsapp.core.composables.verticalSpacer
import com.myapp.adminsapp.ui.theme.AdminsAppTheme
import com.myapp.adminsapp.ui.theme.Saffron

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ResourceAsColor")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen() {
            Scaffold(modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(
                        title = { Text(text = "Add Product") },
                    )
                }) {
                var selectedImageUris by remember {
                    mutableStateOf<List<Uri>>(emptyList())
                }

                val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.PickMultipleVisualMedia(),
                    onResult = { uris -> selectedImageUris = uris }
                )
                var name = remember {
                    mutableStateOf("12")
                }
                var quantity = remember {
                    mutableStateOf("")
                }
                var unit = remember {
                    mutableStateOf("")
                }
                var price = remember {
                    mutableStateOf("")
                }
                var productCategory = remember {
                    mutableStateOf("")
                }

                var productType = remember {
                    mutableStateOf("")
                }

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
                    ProductEditTxtField(title = name, label = "Product name")
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
                        ProductEditTxtField(title = unit, label = "unit", width = 170.dp)

                    }
                    verticalSpacer(dp = 8.dp)
                    Row(
                        modifier = Modifier.width(340.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        ProductEditTxtField(title = price, label = "price", width = 170.dp,)
                        horizontalSpacer(8.dp)
                        ProductEditTxtField(title = unit, label = "no of stock", width = 170.dp)

                    }
                    ProductEditTxtField(title = productCategory, label = "product category")
                    ProductEditTxtField(title = productType, label = "product type")
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

                            }
                        )
                    }
                    LazyRow(modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)){
                        items(selectedImageUris){
                                AsyncImage(model = it,
                                    placeholder = painterResource(id = R.drawable.baseline_add_photo_alternate_24) ,
                                    contentDescription =null,
                                    modifier = Modifier
                                        .width(70.dp)
                                        .height(70.dp))
                        }
                    }

                    Button(onClick = { /*TODO*/ },
                        modifier = Modifier.width(340.dp).height(50.dp),
                        colors = ButtonColors(
                        containerColor = Saffron, contentColor = Color.White, disabledContainerColor = Saffron,
                        disabledContentColor = Saffron
                    )) {
                        Text(text = "Save Product")
                    }

                }


            }

}
@Preview(showBackground = true)
@Composable
fun AddProductScreenPreview() {
    AddProductScreen()
}

