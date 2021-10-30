package ru.wanket.exchange_trading_assistant.ui.widgets

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.wanket.exchange_trading_assistant.entity.RateBaseInfo
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

@Composable
fun RateTitleBar(rateBaseInfo: RateBaseInfo, content: @Composable (PaddingValues) -> Unit) =
    Scaffold(
        topBar = {
            TopAppBar {
                RateView(rateBaseInfo)
            }
        },
        content = content
    )

@Composable
fun RateView(rateBaseInfo: RateBaseInfo) {
    Column(modifier = Modifier.padding(start = 8.dp)) {
        Text(rateBaseInfo.codeName)
        Text(rateBaseInfo.type.displayName, fontSize = 8.sp)
    }
}

@Composable
fun EndArrangementRow(content: @Composable RowScope.() -> Unit) = Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.End,
    content = content
)

@Composable
fun LabeledInfo(label: String, text: String) = Column {
    Text(label, fontSize = 8.sp, fontWeight = FontWeight.Bold)
    Text(text)
}

@Composable
fun ExpandableColumn(
    columnTitle: String,
    content: @Composable ColumnScope.() -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { isExpanded = !isExpanded }
        ) {
            Text(columnTitle)

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    with(Icons.Default) { if (isExpanded) KeyboardArrowLeft else KeyboardArrowDown },
                    null
                )
            }
        }

        if (isExpanded) {
            content()
        }
    }
}

@Composable
fun ShadowedText(text: String, color: Color) = Box {
    Text(
        text, color = Color.DarkGray, modifier = Modifier
            .offset(1.dp, 1.dp)
            .alpha(0.5f)
    )
    Text(text, color = color)
}

@Composable
fun ColumnWithRoundedBackground(
    modifier: Modifier = Modifier,
    alpha: Float = 0.25f,
    content: @Composable ColumnScope.() -> Unit
) = Column(
    modifier = modifier
        .background(
            Color.Gray.copy(alpha = alpha),
            RoundedCornerShape(8.dp)
        )
        .padding(4.dp),
    content = content
)
