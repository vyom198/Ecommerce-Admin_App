package com.myapp.adminsapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.myapp.adminsapp.auth.presentation.LoginCodeConfirmation
import com.myapp.adminsapp.auth.presentation.LoginWithPhone
import com.myapp.adminsapp.auth.presentation.PhoneAuthViewModel
import com.myapp.adminsapp.navigation.composables.AppContent
import com.myapp.adminsapp.ui.theme.AdminsAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AdminsAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val phoneAuthViewModel :PhoneAuthViewModel = hiltViewModel()
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Graph.AUTHENTICATION,
                        route = Graph.ROOT
                    ) {

                        navigation(
                            startDestination = AuthScreen.Login.route,
                            route = Graph.AUTHENTICATION
                        ) {
                            composable(route = AuthScreen.Login.route) {
                                val state by phoneAuthViewModel.state.collectAsState()


                                LaunchedEffect(key1 = Unit) {
                                    if (phoneAuthViewModel.getSignInUser() != null) {
                                        navController.navigate(Graph.HOME)
                                    }
                                }

                                LaunchedEffect(key1 = state.isSignInSuccessful) {
                                    Log.d("state of sign in ", state.isSignInSuccessful.toString())
                                    if (state.isSignInSuccessful) {
                                        Toast.makeText(
                                            this@MainActivity,
                                            "Sign in successful",
                                            Toast.LENGTH_LONG
                                        ).show()

                                        navController.navigate(Graph.HOME)
                                        phoneAuthViewModel.resetState()
                                    }
                                }
                              LoginWithPhone(phoneAuthViewModel =phoneAuthViewModel,
                                  navController = navController,
                                  this@MainActivity
                              )
                            }
                            composable(route = AuthScreen.CodeVerification.route) {
                                LoginCodeConfirmation(
                                    phoneAuthViewModel =phoneAuthViewModel,
                                    navController = navController,
                                    this@MainActivity
                                )

                            }
                            composable(route = Graph.HOME){
                                AppContent(navController = rememberNavController())
                            }

                        }


                    }


                }


            }
        }
    }
}


