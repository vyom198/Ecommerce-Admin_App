package com.myapp.adminsapp.addproduct.presentation

import BottomNavScreen
import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.myapp.adminsapp.MainActivity
import com.myapp.adminsapp.core.common.TestTags
import com.myapp.adminsapp.ui.theme.AdminsAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class AddProductsTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this@AddProductsTest)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp(){
     hiltRule.inject()
        composeRule.activity.setContent {
            val navController = rememberNavController()
             val viewmodel: AddproductViewmodel = hiltViewModel()
            AdminsAppTheme {
                NavHost(
                    navController = navController,
                    startDestination = BottomNavScreen.AddProduct.route
                ) {
                    composable(route =  BottomNavScreen.AddProduct.route) {
                        AddProductScreen(viewmodel = viewmodel )
                    }
                }

            }

        }

    }


     @Test
     fun saveProductButton_IsDisplayed_Or_Not (){
         composeRule.onNodeWithTag(TestTags.SAVEBUTTON).assertIsNotEnabled()
     }

}