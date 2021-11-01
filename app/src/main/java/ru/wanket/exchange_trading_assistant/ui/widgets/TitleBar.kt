package ru.wanket.exchange_trading_assistant.ui.widgets

import android.app.Activity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.wanket.exchange_trading_assistant.Navigator

@Composable
fun Activity.TitleBar(navigator: Navigator, content: @Composable (PaddingValues) -> Unit) =
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(
                            packageManager.getActivityInfo(
                                componentName,
                                0
                            ).labelRes
                        )
                    )
                },
                actions = {
                    IconButton(onClick = { navigator.navigateSearch() }) {
                        Icon(Icons.Default.Search, contentDescription = null)
                    }
                }
            )
        },
        content = content
    )
