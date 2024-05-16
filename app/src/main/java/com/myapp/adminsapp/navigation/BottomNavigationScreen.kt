import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.myapp.adminsapp.addproduct.presentation.AddProductScreen
import com.myapp.adminsapp.home.presentation.HomeScreen
import com.myapp.adminsapp.addproduct.presentation.OrderedProducts


sealed class BottomNavScreen(
    val name : String,
    val id : Int,
    val route : String,
    val selectedIcon : ImageVector?,
    val unselectedIcon : ImageVector?,
 ) {
    object Home : BottomNavScreen(
        name = "Home",
        route= "home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        id = 1

    )
    object Ordered : BottomNavScreen(
        name = "Ordered",
        route= "ordered",
        selectedIcon = Icons.Filled.Check,
        unselectedIcon = Icons.Outlined.Check,
        id = 2
    )



    object  AddProduct : BottomNavScreen(
        name = "addProduct",
        route = "addproduct",
        id = 3,
        selectedIcon = Icons.Filled.Add,
        unselectedIcon = Icons.Outlined.Add
    )

}

object Graph {
    const val ROOT = "root_graph"
    const val AUTHENTICATION = "auth_graph"
    const val HOME = "home_graph"

}

sealed class AuthScreen(val route: String) {
    object Login : AuthScreen(route = "LOGIN")
    object CodeVerification : AuthScreen (route = "codeVerification")

}


@Composable
fun HomeNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = BottomNavScreen.Home.route,
        route = Graph.HOME
       
    ) {
        composable(route = BottomNavScreen.Home.route) {
            HomeScreen()
        }


        composable(route = BottomNavScreen.AddProduct.route) {
            AddProductScreen(
             viewmodel = hiltViewModel()
            )
        }
            composable(route = BottomNavScreen.Ordered.route) {
               OrderedProducts()
            }


    }
}


