package com.myapp.adminsapp.auth.presentation

import AuthScreen
import BottomNavScreen
import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontVariation.width
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.myapp.adminsapp.R
import com.myapp.adminsapp.core.common.ResultState
import com.myapp.adminsapp.core.common.navigateSingleTopTo
import com.myapp.adminsapp.core.common.showMsg
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun LoginWithPhone(
    phoneAuthViewModel: PhoneAuthViewModel,
    navController: NavHostController,
    activity: Activity
) {
    var isDialog by remember{ mutableStateOf(false)}
    var phoneNo by remember {
        mutableStateOf("")
    }

    val scope = rememberCoroutineScope()
  Column(
      modifier = Modifier.fillMaxSize(),
      horizontalAlignment = Alignment.CenterHorizontally
  ) {
     AsyncImage(model = R.drawable.image_prod,
         modifier = Modifier
             .height(450.dp)
             .fillMaxWidth(),
         contentDescription =null )
       AsyncImage(model = R.drawable.admins_app_icon,
           contentDescription =null ,
           modifier = Modifier
               .height(60.dp)
               .width(60.dp))
      Spacer(modifier = Modifier.height(8.dp))
      Text(text = "India's last minute app",
           fontWeight = FontWeight.Bold, fontSize = 26.sp,
          fontFamily = FontFamily.Default,

          )
      Spacer(modifier = Modifier.height(8.dp))

          Text(text = "Log in or sign up",
              fontWeight = FontWeight.ExtraBold, fontSize = 20.sp,
              fontFamily = FontFamily.Default,

          )
      Spacer(modifier = Modifier.height(20.dp))
      OutlinedTextField(value = phoneNo, onValueChange = {
          phoneNo = it
      },modifier = Modifier.fillMaxWidth(0.8f),
          placeholder = {
            Text(text = "Enter mobile number")
          },)
      Spacer(modifier = Modifier.height(20.dp))
      Button(onClick = {
          scope.launch(Dispatchers.Main){
              phoneAuthViewModel.createUserWithPhone(
                  phoneNo,
                  activity
              ).collect{
                  when(it){
                      is ResultState.Success->{
                          isDialog = false
                          activity.showMsg(it.data)
                          navController.navigateSingleTopTo(AuthScreen.CodeVerification.route)
                      }
                      is ResultState.Failure->{
                          isDialog = false
                          activity.showMsg(it.exception.toString())
                      }
                      ResultState.Loading->{
                          isDialog = true
                      }
                  }
              }
          }


      }, modifier =
      Modifier
          .fillMaxWidth(0.8f)
          .height(50.dp),

      ) {
          Text(text = "Continue")
      }

  }


}
@Preview(showBackground = true)
@Composable
fun loginphonepreview() {
    //LoginWithPhone()
}