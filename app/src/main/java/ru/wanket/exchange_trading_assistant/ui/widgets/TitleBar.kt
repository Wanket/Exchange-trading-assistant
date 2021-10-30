package ru.wanket.exchange_trading_assistant.ui.widgets

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.wanket.exchange_trading_assistant.ui.search.SearchActivity

@Composable
fun Activity.TitleBar(content: @Composable (PaddingValues) -> Unit) = Scaffold(
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
                IconButton(onClick = {
                    startActivity(
                        Intent(
                            this@TitleBar,
                            SearchActivity::class.java
                        )
                    )
                }) {
                    Icon(Icons.Default.Search, contentDescription = null)
                }
            }
        )
    },
    content = content
)
