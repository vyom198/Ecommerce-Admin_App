package com.myapp.adminsapp.allProducts.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AllProducts() {

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "All Products") },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Default.ArrowBackIosNew, contentDescription =null )
                    }
                }
                )

        }
    ) { padding->

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(padding), horizontalAlignment = Alignment.CenterHorizontally) {

            LazyVerticalGrid(columns = GridCells.Fixed(3)){
              //  items()

            }
        }
    }

}