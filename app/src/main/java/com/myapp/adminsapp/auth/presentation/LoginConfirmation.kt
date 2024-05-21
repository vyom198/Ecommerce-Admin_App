package com.myapp.adminsapp.auth.presentation

import Graph
import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.myapp.adminsapp.auth.data.SignInResult

import com.myapp.adminsapp.core.common.ResultState
import com.myapp.adminsapp.core.common.showMsg
import com.myapp.adminsapp.core.composables.OtpView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginCodeConfirmation(
    phoneAuthViewModel: PhoneAuthViewModel,
    navController: NavHostController,
    activity: Activity
) {
    var otp by remember {
        mutableStateOf("")
    }
    val scope = rememberCoroutineScope()
Scaffold (
    topBar = {
        TopAppBar(title = { Text(text ="otp verification")
            }, navigationIcon = {
                IconButton(onClick ={
                    navController.popBackStack()
                }) {
                   Icon(imageVector = Icons.Default.ArrowBack, contentDescription =null )
                }
        })
    }
){
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(it), horizontalAlignment = Alignment.CenterHorizontally){

        Spacer(modifier = Modifier.height(29.dp))
        Text(text = "we have sent code on your +91XXXXXXXXXX")
        Spacer(modifier = Modifier.height(29.dp))
        OtpView(onOtpTextChange = {
            otp = it
        }, otpText = otp)
        Spacer(modifier = Modifier.height(29.dp))
        Button(
            onClick = {
                scope.launch(Dispatchers.Main) {
                    phoneAuthViewModel.signInWithCredential(
                        otp
                    ).collect { signInresult ->
                        when (signInresult) {
                            is ResultState.Success -> {
                                phoneAuthViewModel.onSignInResult(signInresult.data)
                                navController.navigate(Graph.HOME)
                            }

                            is ResultState.Failure -> {
                                activity.showMsg(signInresult.exception.toString())
                            }

                            else -> {}
                        }
                    }
                }

            },
            modifier =
            Modifier
                .fillMaxWidth(0.8f)
                .height(40.dp),

            ) {
            Text(text = "Login")
        }
    }
}

}
@Preview(showBackground = true)
@Composable
fun loginphonecodepreview() {
    //LoginCodeConfirmation()
}