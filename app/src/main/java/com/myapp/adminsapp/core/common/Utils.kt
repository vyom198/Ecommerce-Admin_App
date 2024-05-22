package com.myapp.adminsapp.core.common


import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.RectangleShape
import com.myapp.adminsapp.addproduct.domain.RealtimeProduct
import com.myapp.adminsapp.allProducts.domain.DomainProduct


    const val OTP_VIEW_TYPE_NONE = 0
    const val OTP_VIEW_TYPE_UNDERLINE = 1
    const val OTP_VIEW_TYPE_BORDER = 2



fun RealtimeProduct.toGetAllProducts() : DomainProduct{
    return  DomainProduct(
       item = DomainProduct.Product(
           product = item?.product,
           price = item?.price,
           productUnit = item?.productUnit,
           productImageUris = item?.productImageUris

       ),
        key = key,

    )
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            inclusive = true
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
        launchSingleTop = true
    }

fun Context.showMsg(
    msg:String,
    duration:Int = Toast.LENGTH_SHORT
) = Toast.makeText(this,msg,duration).show()

val Unitlist = listOf<String>(
    "Kg","ml","Ltr","Packets","gm"
)
val ProductCategory = listOf(
    "Vegetables & Fruits", "Atta,Rice & Dal" ,
    "Oil,Ghee & Masala" , "Sweets & Chocolates", "Chips & Namkeen",
    "Bath & Body" , "Hair", "Cleaners & Repellents" , "Electronics",
    "Dairy,Milk & eggs"
)
 val ProductType = listOf(
     "Grocery & Kitchen" , "Snacks & Drinks" , "Beauty & Personal Care","Household Essentials"
 )

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchProduct() {
    var query  by remember{
        mutableStateOf("")
    }
    var active  by remember{
        mutableStateOf(false)
    }
    SearchBar(
        query = query,
        onQueryChange = { query = it },
        onSearch = {

        },
        active = active,
        onActiveChange = {
            active = !active
        },
        placeholder = {
            Text(text = "Search")
        },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search , contentDescription =null )
        }, shape = RectangleShape,

        ) {


    }
}


