package com.myapp.adminsapp.home.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myapp.adminsapp.addproduct.presentation.AddProductScreen

import com.myapp.adminsapp.core.common.ProductCategory
import com.myapp.adminsapp.core.composables.CardItem
import com.myapp.adminsapp.core.composables.horizontalSpacer
import com.myapp.adminsapp.core.composables.verticalSpacer
import com.myapp.adminsapp.ui.theme.Saffron

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onClickProducts : () -> Unit ,
    onClickCategories : () -> Unit
) {

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Home") }
                , actions = {
                    Icon(imageVector = Icons.Default.Logout, contentDescription = null)
                }
            )
        }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            verticalSpacer()

            verticalSpacer()
            LazyVerticalGrid(GridCells.Fixed(2),modifier = Modifier.padding(
                horizontal = 10.dp, vertical = 10.dp
            )
                ) {


                item {
                    CardItem(onClick = { /*TODO*/ }, text = "users" )
                }
                item {
                     CardItem(onClick = onClickProducts, text = "All Products" )
                }

                item {
                    CardItem(onClick = onClickCategories, text = "Categories", )

                }
                item {
                    CardItem(onClick = { /*TODO*/ }, text = "Cancelled Orders", )

                }
                item {
                    CardItem(onClick = { /*TODO*/ }, text = "Delivered Products", )

                }
                item {
                    CardItem(onClick = { /*TODO*/ }, text = "Banners", )

                }
                item{
                    CardItem(onClick =  {}, text =  "Pending Orders")
                }
            }

          }


        }
    }

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    //HomeScreen()
}
