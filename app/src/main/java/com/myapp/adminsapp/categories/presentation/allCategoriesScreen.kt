package com.myapp.adminsapp.categories.presentation

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Category
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.myapp.adminsapp.allProducts.presentation.LazyProductItem
import com.myapp.adminsapp.categories.data.Category
import com.myapp.adminsapp.core.common.ProductType
import com.myapp.adminsapp.core.common.showMsg
import com.myapp.adminsapp.core.composables.CommonDialog
import com.myapp.adminsapp.core.composables.ProductEditTxtField
import com.myapp.adminsapp.core.composables.TextfielDropDown
import com.myapp.adminsapp.core.composables.horizontalSpacer
import com.myapp.adminsapp.core.composables.verticalSpacer
import com.myapp.adminsapp.ui.theme.Saffron
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable

fun CategoriesScreen(
    viewmodel: CategoryViewmodel,
    onBackClick : () -> Unit
) {
    val ImgUploadState by viewmodel.Imgstate.collectAsState()
    val categoryState by viewmodel.Categorystate.collectAsState()
    val productCategory = remember {
        viewmodel.productCategory
    }
    val productType = remember {
        viewmodel.productType
    }

    var reset by remember {
        mutableStateOf(false)
    }
    val expandedValue = remember { mutableStateOf(false) }
        val showDialog = viewmodel.Imgstate.value.isLoading
        var categoryDialog  by remember {
            mutableStateOf(false)
        }

        var selectedImageUri by remember { viewmodel.selectedImageuri }

        val context = LocalContext.current
        Scaffold(
            topBar = {
                TopAppBar(title = { Text(text = "All Products") },
                    navigationIcon = {
                        IconButton(onClick = {
                            onBackClick.invoke()
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = null
                            )
                        }
                    }, actions = {
                        IconButton(onClick = {
                            categoryDialog = true
                        }) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = null)
                        }
                    })
            }
        ) { padding ->
            LaunchedEffect(key1 = ImgUploadState) {
                Log.d("add product", showDialog.toString())
                if (ImgUploadState.success != null) {
                    context.showMsg(msg = ImgUploadState.success.toString())
                }
            }
            val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.PickVisualMedia(),
                onResult = { uri ->
                    uri?.let {
                        viewmodel.addImageToStorage(it)
                        selectedImageUri = it
                    }

                }
            )
            runBlocking {
                if (reset) {
                    viewmodel.resetFields()
                }
            }
            if (showDialog) {
                Log.d("dialog", showDialog.toString())
                CommonDialog()
            }
            if(categoryDialog){
                Log.d("category dialog ", categoryDialog.toString())
                AddCategorydialog(CategoryName = productCategory,
                    CategoryType = productType, expanded=expandedValue , onIconClick = {
                        singlePhotoPickerLauncher.launch(
                          PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }, selectedImageUri = viewmodel.selectedImageuri ,onDismiss = {
                        categoryDialog = false
                    }, onInsert = {
                        viewmodel.insertItem(
                            Category(
                                CategoryName = productCategory.value,
                                CategoryType = productType.value, image = viewmodel.imageUrl.value,

                            )
                        )
                        reset = true
                        context.showMsg("category added", Toast.LENGTH_SHORT)
                        categoryDialog = false
                    })
            }
            if (categoryState.error != null){
                Text(text = "error occoured") }
            if(categoryState.isLoading){
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    CircularProgressIndicator() }
            }else{
                val result = categoryState.item
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)) {

                    LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.fillMaxSize().padding(10.dp)){
                        items(result){

                        }

                    }
                }
            }

        }
    }

    @Composable
    fun AddCategorydialog(
        CategoryName: MutableState<String>,
        CategoryType: MutableState<String>,
        expanded: MutableState<Boolean>,
        onIconClick : () -> Unit,
        selectedImageUri : MutableState<Uri>,
        onInsert : ()-> Unit ,
        onDismiss : ()-> Unit
    ) {
        val isFormValid = CategoryName.value.isNotBlank() && CategoryType.value.isNotBlank()
        Dialog(onDismissRequest = onDismiss) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .padding(horizontal = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,

                    ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "add category icon")
                        horizontalSpacer()
                        Icon(
                            imageVector = Icons.Default.AddPhotoAlternate,
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                onIconClick.invoke()
                            }
                        )

                    }
                    verticalSpacer(12.dp)
                    AsyncImage(model = selectedImageUri.value , contentDescription =null,
                        modifier = Modifier
                            .width(60.dp)
                            .height(60.dp))
                    ProductEditTxtField(title = CategoryName, label = "Add Category ")
                    verticalSpacer()
                    TextfielDropDown(
                        category = ProductType, title = CategoryType, label = "product type",
                        expanded = expanded, leadingIcon = Icons.Default.Category
                    )
                    verticalSpacer(36.dp)
                    Button(onClick = {
                        onInsert.invoke()
                    },
                        colors = ButtonColors(
                            containerColor = Saffron, contentColor = Color.White, disabledContainerColor = Color.Transparent,
                            disabledContentColor = Color.Transparent),
                        enabled = isFormValid) {
                        Text(text = "Add")
                    }
                }


            }

        }
    }

