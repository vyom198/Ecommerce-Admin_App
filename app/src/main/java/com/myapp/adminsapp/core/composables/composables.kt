package com.myapp.adminsapp.core.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CardColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.myapp.adminsapp.core.common.OTP_VIEW_TYPE_BORDER
import com.myapp.adminsapp.core.common.OTP_VIEW_TYPE_UNDERLINE
import com.myapp.adminsapp.ui.theme.LightViolet

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextfielDropDown(
    category: List<String>,
    leadingIcon : ImageVector? = null,
    title: MutableState<String>,
    width: Dp = 340.dp,
    height: Dp = 70.dp,
    label: String = "",
    hint : String = "",
    expanded : MutableState<Boolean>
) {
    ExposedDropdownMenuBox(expanded =expanded.value
        , onExpandedChange ={
           expanded.value =  !expanded.value
        } ) {
        OutlinedTextField(value = title.value, onValueChange = {
            title.value = it
        }, modifier = Modifier
            .width(width)
            .height(height)
            .clip(RoundedCornerShape(8.dp))
            .menuAnchor(),
            label = { Text(text = label) },
            readOnly = true,
            placeholder = { Text(text = hint)},
            leadingIcon = {
                if (leadingIcon != null) {
                    Icon(imageVector = leadingIcon, contentDescription =null )
                }
            },
            trailingIcon =  {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value)
            }

            )
        ExposedDropdownMenu(expanded = expanded.value,
            onDismissRequest = {expanded.value = false }) {
            category.forEach {
                DropdownMenuItem(text = { Text(text = it ) },
                    onClick = {  title.value = it
                         expanded.value = false
                    })
            }
        }
        
    }



}
@Composable
fun CommonDialog() {
    Dialog(
        onDismissRequest = {}
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(100.dp)
                .background(Color.White, shape = RoundedCornerShape(8.dp))
        ) {
            CircularProgressIndicator()
        }
    }
}



@Composable
fun CardItem(
    onClick : () -> Unit,
    text : String ,

    ) {
    ElevatedCard(
        onClick = onClick, modifier =
        Modifier
            .height(250.dp)
            .width(250.dp)
            .padding(horizontal = 10.dp, vertical = 10.dp),
        colors = CardColors(containerColor = LightViolet , contentColor = Color.Black,
            disabledContainerColor = LightViolet , disabledContentColor = Color.Black)
    ) {
        Box(
            modifier =
            Modifier.fillMaxSize()

        ) {
            Text(text = text, modifier = Modifier.align(Alignment.Center))

        }
    }
}


@Composable
fun OtpView(
    modifier  : Modifier= Modifier,
    otpText: String = "",
    charColor: Color = Color.Blue,
    charBackground: Color = Color.Transparent,
    charSize: TextUnit = 20.sp,
    containerSize: Dp = charSize.value.dp * 2,
    otpCount: Int = 6,
    type: Int = OTP_VIEW_TYPE_BORDER,
    enabled: Boolean = true,
    password: Boolean = false,
    passwordChar: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
    onOtpTextChange: (String) -> Unit
) {
    BasicTextField(
        modifier = modifier,
        value = otpText,
        onValueChange = {
            if (it.length <= otpCount) {
                onOtpTextChange.invoke(it)
            }
        },
        enabled = enabled,
        keyboardOptions = keyboardOptions,
        decorationBox = {
            Row(horizontalArrangement = Arrangement.SpaceAround) {
                repeat(otpCount) { index ->
                    Spacer(modifier = Modifier.width(2.dp))
                    CharView(
                        index = index,
                        text = otpText,
                        charColor = charColor,
                        charSize = charSize,
                        containerSize = containerSize,
                        type = type,
                        charBackground = charBackground,
                        password = password,
                        passwordChar = passwordChar,
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                }
            }
        })
}

@Composable
private fun CharView(
    index: Int,
    text: String,
    charColor: Color,
    charSize: TextUnit,
    containerSize: Dp,
    type: Int = OTP_VIEW_TYPE_UNDERLINE,
    charBackground: Color = Color.Cyan,
    password: Boolean = false,
    passwordChar: String = ""
) {
    val modifier = if (type == OTP_VIEW_TYPE_BORDER) {
        Modifier
            .size(containerSize)
            .border(
                width = 1.dp,
                color = charColor,
                shape = MaterialTheme.shapes.medium
            )
            .padding(bottom = 4.dp)
            .background(charBackground)
    } else Modifier
        .width(containerSize)
        .background(charBackground)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        val char = when {
            index >= text.length -> ""
            password -> passwordChar
            else -> text[index].toString()
        }
        Text(
            text = char,
            color = Color.Black,
            modifier = modifier.wrapContentHeight(),
            style = MaterialTheme.typography.displayMedium,
            fontSize = charSize,
            textAlign = TextAlign.Center,
        )
        if (type == OTP_VIEW_TYPE_UNDERLINE) {
            Spacer(modifier = Modifier.height(2.dp))
            Box(
                modifier = Modifier
                    .background(charColor)
                    .height(1.dp)
                    .width(containerSize)
            )
        }
    }
}


