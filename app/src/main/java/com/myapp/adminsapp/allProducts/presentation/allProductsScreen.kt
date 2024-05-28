package com.myapp.adminsapp.allProducts.presentation

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.myapp.adminsapp.allProducts.domain.DomainProduct
import com.myapp.adminsapp.core.common.formatCurrency
import com.myapp.adminsapp.core.composables.verticalSpacer
import com.myapp.adminsapp.ui.theme.AdminsAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AllProducts(
    viewmodel: AllProductViewmodel,
    onBackClick : ()-> Unit
) {
    val state by viewmodel.state.collectAsState()
    Log.d("screen", state.toString())

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "All Products") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription =null )
                    }
                }
                )

        }
    ) { padding->
       if (state.error != null){
        Text(text = "error occoured") }
        if(state.isLoading){
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                CircularProgressIndicator() }
        }else{
            val result = state.item
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(padding)) {

                LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.fillMaxSize().padding(10.dp)){
                   items(result){
                       LazyProductItem(item = it.item!!)
                   }

                }
            }
        }


    }

}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun LazyProductItem(
    item : DomainProduct.Product
) {
    val pagerState = rememberPagerState(
        pageCount = item.productImageUris?.size ?: 0
    )


    val formattedAmount =  formatCurrency(item.price?.toDouble() ?: 0.0)
    Card(modifier = Modifier
        .width(200.dp)
        .height(330.dp).padding(8.dp),
        shape = RectangleShape,

        ) {
        HorizontalPager(state = pagerState,
            verticalAlignment = Alignment.Bottom,

        ) {page ->
            AsyncImage(
                modifier = Modifier
                    .height(190.dp)
                    .fillMaxWidth(),
                model = item.productImageUris?.get(page), contentDescription = "product image")

            HorizontalPagerIndicator(pagerState =pagerState ,
                activeColor = MaterialTheme.colorScheme.primary, inactiveColor =
                MaterialTheme.colorScheme.inversePrimary,
                spacing = 2.dp,
                indicatorWidth = 5.dp,
               modifier = Modifier.align(Alignment.BottomEnd).padding(5.dp)
            )

        }


        Text(text = item.product.toString() , maxLines = 2,
            modifier = Modifier
                .height(50.dp)
                .padding( horizontal = 8.dp, vertical = 4.dp), lineHeight = 14.sp, fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            overflow = TextOverflow.Ellipsis,)

        Text(text = "${item.productQuantity} ${item.productUnit}"
            ,modifier = Modifier.padding(horizontal = 8.dp), fontSize = 13.sp,fontWeight = FontWeight.Light, )

        Row(horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp)) {
            Text(text = formattedAmount,fontWeight = FontWeight.Medium,
                fontSize = 14.sp,)
            OutlinedButton(onClick = { /*TODO*/ },
                modifier = Modifier.padding(bottom = 8.dp) ) {
                Text(text = "Edit")
            }
        }


    }

}
//@Preview(showBackground = true)
//@Composable
//fun ProductItempreview() {
//    AdminsAppTheme {
//        Column(modifier = Modifier.fillMaxSize(),
//            verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
//            LazyProductItem(item =DomainProduct.Product(
//                product = "hello this is macroni cheeese fhdifdfdfdffd", price = 20, productImageUris =
//                listOf("https://firebasestorage.googleapis.com/v0/b/shopify-3a44a.appspot.com/o/Images%2Fimages%2Fe7bee9d0-be1f-4947-bd0b-6423ce4bc39f?alt=media&token=e604f9cb-deaa-45f9-98cb-cfe1e4b23094",
//                    "https://firebasestorage.googleapis.com/v0/b/shopify-3a44a.appspot.com/o/Images%2Fimages%2F0b24f363-68d7-45b1-b96d-066ab897033e?alt=media&token=34a011cd-86b7-4b73-beeb-a293c2c225f9",
//                    "https://firebasestorage.googleapis.com/v0/b/shopify-3a44a.appspot.com/o/Images%2Fimages%2F7e16864e-cee3-4987-ac21-4b37e414afe1?alt=media&token=f1523b7e-948f-4dbd-87df-0c59909e216e",
//                    "https://firebasestorage.googleapis.com/v0/b/shopify-3a44a.appspot.com/o/Images%2Fimages%2F2e86e330-f571-4429-b613-10b2a4b9fafb?alt=media&token=3cf49dca-846c-4458-9e41-42f45457bd57"
//                ),
//                productUnit = "ml",
//                productQuantity = 250
//            ) )
//        }
//
//    }
//
//}