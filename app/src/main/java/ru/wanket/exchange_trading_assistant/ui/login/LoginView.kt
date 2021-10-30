package ru.wanket.exchange_trading_assistant.ui.login

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.text.isDigitsOnly
import ru.wanket.exchange_trading_assistant.ui.theme.ExchangeTradingAssistantTheme
import ru.wanket.exchange_trading_assistant.ui.widgets.FilteredTextField

@Composable
fun Ui(viewModel: LoginViewModel) {
    ExchangeTradingAssistantTheme {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            val (textField, label, button) = createRefs()

            Text(
                if (viewModel.isLogin) "Welcome back!" else "Welcome! Let's create a pin",
                modifier = Modifier.constrainAs(label) {
                    centerHorizontallyTo(parent)
                    bottom.linkTo(textField.top, 8.dp)
                }
            )

            FilteredTextField(
                value = viewModel.pinCode,
                onValueChange = {
                    viewModel.apply {
                        pinCode = it
                        isPinCodeWrong = false
                    }
                },
                label = {
                    Text(
                        if (!viewModel.isPinCodeWrong) {
                            "Enter pin code"
                        } else {
                            "Invalid pin code. Try again"
                        }
                    )
                },
                isError = viewModel.isPinCodeWrong,
                modifier = Modifier.constrainAs(textField) {
                    start.linkTo(parent.start, 8.dp)
                    end.linkTo(parent.end, 8.dp)
                    centerVerticallyTo(parent)
                },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    if (viewModel.pinCode.length == 4) {
                        viewModel.onLogin()
                    }
                }),
                filter = { it.length <= 4 && it.isDigitsOnly() }
            )

            Button(
                onClick = { viewModel.onLogin() },
                modifier = Modifier.constrainAs(button) {
                    centerHorizontallyTo(parent)
                    top.linkTo(textField.bottom, 8.dp)
                },
                enabled = viewModel.pinCode.length == 4
            ) {
                Text(if (viewModel.isLogin) "Login" else "Sign Up")
            }
        }
    }
}
