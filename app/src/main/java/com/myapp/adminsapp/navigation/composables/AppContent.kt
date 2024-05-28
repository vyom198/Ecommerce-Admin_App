package com.myapp.adminsapp.navigation.composables
import BottomNavScreen
import HomeNavGraph
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.myapp.adminsapp.core.common.navigateSingleTopTo


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppContent(
    navController : NavHostController,

    ) {
    val screens = listOf(
        BottomNavScreen.Home,
        BottomNavScreen.AddProduct,
        BottomNavScreen.Ordered
    )
    var selectedItemIndex by rememberSaveable {
        mutableStateOf( 0)
    }
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination

    val showBottomBar = currentDestination?.route in screens.map { it.route }
    Scaffold(
        bottomBar = {
            if (showBottomBar){
                NavigationBar(modifier = Modifier.height(70.dp)) {
                    screens.forEach{  item ->
                        NavigationBarItem(
                            selected = selectedItemIndex == item.id,
                            onClick = {  selectedItemIndex = item.id
                                navController.navigateSingleTopTo(item.route)
                            },
                            icon = {
                                Icon(
                                    imageVector = (if (item.id == selectedItemIndex) {
                                        item.selectedIcon!!
                                    } else item.unselectedIcon!!),
                                    contentDescription = item.name
                                )
                            })
                    }
                }
            }

        }
    ){
        HomeNavGraph(navController = navController)
    }
}





