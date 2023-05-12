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
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.riders.sportfinder.R
import ru.riders.sportfinder.ui.theme.LightGreen
import ru.riders.sportfinder.ui.theme.White

@Composable
fun AuthorizationScreen(
    viewModel: MainActivityViewModel?,
    navHostController: NavHostController?
) {
    var login: String by remember { mutableStateOf("") }
    var password: String by remember { mutableStateOf("") }

    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 48.dp),
        contentAlignment = Alignment.TopCenter
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
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
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor = LightGreen
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
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor = LightGreen
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
                onClick = {
                    viewModel?.trySignIn(
                        login,
                        password,
                        onSuccess = {
                            Toast.makeText(context, "Вы авторизованы", Toast.LENGTH_SHORT).show()
                        },
                        onFailed = {
                            Toast.makeText(context, "Ошибка авторизации", Toast.LENGTH_SHORT).show()
                        }
                    )
                },
                modifier = Modifier.fillMaxWidth(0.8f),
                colors = ButtonDefaults.buttonColors(backgroundColor = LightGreen)
            ) {
                Text(text = "ВОЙТИ", color = White)
            }

            Text(
                text = "ИЛИ ЗАРЕГИСТРИРОВАТЬСЯ",
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .clickable {
                        navHostController?.navigate(Screens.REG_SCREEN.route)
                    },
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = LightGreen,
            )
        }
    }
}

@Composable
@Preview
fun AuthorizationPreview() {
    AuthorizationScreen(null, null)
}


