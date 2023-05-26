package ru.riders.sportfinder.screen.registration_screen

import android.widget.Toast
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ru.riders.sportfinder.R
import ru.riders.sportfinder.screen.Screens
import ru.riders.sportfinder.screen.ui.theme.LightGreen
import ru.riders.sportfinder.screen.ui.theme.White

@Composable
fun RegistrationScreen(
    navHostController: NavHostController?,
    viewModel: RegistrationViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val (login, password, errorMessage, isLoading) = viewModel.state.value
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
                    viewModel.updateLogin(it)
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
                    viewModel.updatePassword(it)
                })

            Button(
                onClick = {
                          viewModel.trySignUpUser(
                              onSuccess = {
                                  Toast.makeText(context, "Вы авторизованы", Toast.LENGTH_SHORT).show()
                                  navHostController?.navigate(Screens.PROFILE_SCREEN.route)
                              }
                          )
/*                    viewModel.trySignUp(
                        login,
                        password,
                        onSuccess = {
                            Toast.makeText(context, "Вы авторизованы", Toast.LENGTH_SHORT).show()
                            navHostController?.navigate(Screens.PROFILE_SCREEN.route)
                        },
                        onFailed = {
                            Toast.makeText(context, "Ошибка регистрации", Toast.LENGTH_SHORT).show()
                        }
                    )*/
                },
                modifier = Modifier.fillMaxWidth(0.8f),
                colors = ButtonDefaults.buttonColors(backgroundColor = LightGreen)
            ) {
                Text(text = "ПОДТВЕРДИТЬ", color = White)
            }

            Text(
                text = "ИЛИ ВОЙТИ",
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .clickable {
                        navHostController?.navigate(Screens.AUTH_SCREEN.route)
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
fun RegistrationPreview() {
    RegistrationScreen(null)
}

