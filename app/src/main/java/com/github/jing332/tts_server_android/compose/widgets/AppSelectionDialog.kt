package com.github.jing332.tts_server_android.compose.widgets

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.github.jing332.tts_server_android.R
import com.github.jing332.tts_server_android.utils.clickableRipple

@Composable
fun AppSelectionDialog(
    onDismissRequest: () -> Unit, title: @Composable () -> Unit,
    value: Any,
    values: List<Any>,
    entries: List<String>,

    buttons: @Composable BoxScope.() -> Unit = {
        TextButton(onClick = onDismissRequest) { Text(stringResource(id = R.string.close)) }
    },

    onValueSame: (Any, Any) -> Boolean = { a, b -> a == b },
    onClick: (Any, String) -> Unit,
) {
    AppDialog(
        title = title,
        content = {
            val state = rememberLazyListState()
            LaunchedEffect(Unit) {
                state.scrollToItem(values.indexOfFirst { onValueSame(it, value) })
            }

            LazyColumn(state = state) {
                itemsIndexed(entries) { i, v ->
                    val current = values[i]
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .clip(MaterialTheme.shapes.medium)
                            .clickableRipple {
                                onClick(current, v)
                            }
                            .minimumInteractiveComponentSize()
                    ) {
                        val isSelected = onValueSame(value, current)
                        Text(
                            v,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(4.dp),
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Unspecified
                        )
                    }
                }
            }
        },
        buttons = buttons, onDismissRequest = onDismissRequest,
    )
}