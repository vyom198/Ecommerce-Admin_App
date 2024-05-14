package com.myapp.adminsapp.core.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun verticalSpacer(dp : Dp = 18.dp) {
    Spacer(modifier = Modifier.height(dp))
}

@Composable
fun ProductEditTxtField(
    title: MutableState<String>,
    width: Dp = 340.dp,
    height: Dp = 70.dp,
    label: String,

){
    OutlinedTextField(value = title.value, onValueChange = {
        title.value = it
    }, modifier = Modifier
        .width(width)
        .height(height)
        .clip(RoundedCornerShape(8.dp)),
        label = { Text(text = label) },

        )
}

@Composable
fun horizontalSpacer(dp: Dp = 8.dp) {
    Spacer(modifier = Modifier.width(dp))
}
