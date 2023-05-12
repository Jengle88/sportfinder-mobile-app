package ru.riders.sportfinder.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.riders.sportfinder.R
import ru.riders.sportfinder.ui.theme.Typography

@Composable
fun Authorization() {
    var login: String by remember { mutableStateOf("") }
    var password: String by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 20.dp),
        contentAlignment = Alignment.BottomCenter
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(R.drawable.sportfinder_logo),
                contentDescription = "logo",
                alignment = Alignment.Center,
            )

            TextField(
                value = login,
                shape = RoundedCornerShape(
                    10
                ),
                singleLine = true,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(0.8f),
                placeholder = {
                    Text(text = "ЛОГИН")
                },
                onValueChange = {
                    login = it
                })

            TextField(
                value = password,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(0.8f),
                shape = RoundedCornerShape(
                    10
                ),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                placeholder = {
                    Text(text = "ПАРОЛЬ")
                },
                onValueChange = {
                    password = it
                })

            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text(text = "ВОЙТИ")
            }

            Text(
                text = "Или зарегистрироваться",
                modifier = Modifier
                    .fillMaxSize(0.8f)
                    .clickable {
                        /*TODO*/
                    },
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
            )

        }
    }

}

@Composable
@Preview
fun AuthorizationPreview() {
    Authorization()
}


