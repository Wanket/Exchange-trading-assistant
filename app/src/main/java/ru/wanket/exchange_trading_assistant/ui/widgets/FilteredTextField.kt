package ru.wanket.exchange_trading_assistant.ui.widgets

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun FilteredTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    filter: (String) -> Boolean
) {
    OutlinedTextField(
        value = value,
        label = label,
        isError = isError,
        modifier = modifier,
        enabled = enabled,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        onValueChange = {
            if (filter(it)) {
                onValueChange(it)
            }
        },
    )
}

private val doubleRegex = Regex("^((0|[1-9]\\d*)(\\.\\d*)?)?\$")

fun isPositiveDoubleFilter(str: String) = doubleRegex.containsMatchIn(str)
