package ru.riders.sportfinder.screen.auth_screens.authorization_screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.riders.sportfinder.R
import ru.riders.sportfinder.screen.ui.theme.SportFinderLightColorScheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AuthorizationScreen(
    navigateToProfileScreen: () -> Unit,
    navigateToRegistrationScreen: () -> Unit,
    viewModel: AuthorizationViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    // Проверка на то, что пользователь уже заходил и его токен активен
    LaunchedEffect(key1 = true) {
        viewModel.checkTokenValidity(
            onValidToken = {
                Toast.makeText(context, "Вход выполнен", Toast.LENGTH_SHORT).show()
                navigateToProfileScreen()
            },
            onOutdatedToken = {
                Toast.makeText(context, "Ошибка получения токена или токен недействителен", Toast.LENGTH_SHORT).show()
            }
        )
    }

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
            val (loginFieldFocus, passwordFieldFocus) = remember { FocusRequester.createRefs() }

            TextField(
                value = login,
                shape = RoundedCornerShape(10),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor = SportFinderLightColorScheme.primary
                ),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                singleLine = true,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(0.8f)
                    .focusRequester(loginFieldFocus)
                    .focusProperties { next = passwordFieldFocus },
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
                    .fillMaxWidth(0.8f)
                    .focusRequester(passwordFieldFocus),
                shape = RoundedCornerShape(10),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor = SportFinderLightColorScheme.primary
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
                    viewModel.trySignInUser(
                        onSuccess = {
                            Toast.makeText(context, "Вы авторизованы", Toast.LENGTH_SHORT).show()
                            navigateToProfileScreen()
                        }
                    )
                },
                modifier = Modifier.fillMaxWidth(0.8f),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = SportFinderLightColorScheme.primary
                )
            ) {
                if (isLoading) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(20.dp)
                                .align(Alignment.Center),
                            color = SportFinderLightColorScheme.onPrimary
                        )
                    }
                } else
                    Text(text = "ВОЙТИ", color = SportFinderLightColorScheme.onPrimary)
            }

            Text(
                text = "ИЛИ ЗАРЕГИСТРИРОВАТЬСЯ",
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .clickable {
                        navigateToRegistrationScreen()
                    },
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = SportFinderLightColorScheme.primary,
            )
        }
    }
}

@Composable
@Preview
fun AuthorizationPreview() {
    AuthorizationScreen({}, {})
}


