package com.example.treedatabase.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.treedatabase.R
import com.example.treedatabase.presentation.model.NodeUi
import com.example.treedatabase.presentation.ui.theme.Pink80
import com.example.treedatabase.presentation.ui.theme.PurpleGrey40
import com.example.treedatabase.presentation.ui.theme.PurpleGrey80
import com.example.treedatabase.presentation.ui.theme.Red

@Composable
fun MainScreen(
    modifier: Modifier,
    viewModel: MainScreenViewModel = viewModel()
) {
    val screenState by viewModel.screenState.collectAsState()

    Column(modifier = modifier.padding(8.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            Text(
                text = stringResource(R.string.cached_elements),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(1f),
                color = PurpleGrey40
            )

            Button(
                onClick = { viewModel.resetCache() },
                modifier = Modifier
                    .alpha(if (screenState.cacheLines.isNotEmpty()) 1f else 0f)
                    .align(Alignment.CenterVertically),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Red,
                )
            ) {
                Text(stringResource(R.string.reset_cache))
            }

        }
        TreeView(
            modifier = Modifier
                .weight(1f)
                .background(Pink80)
                .fillMaxWidth()
                .padding(10.dp),
            nodes = screenState.cacheLines.values.toList(),
            onItemClick = { viewModel.cacheItemClick(it) },
            selectedId = screenState.selectedCacheId
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.controls),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            color = PurpleGrey40
        )
        ButtonsBar(
            modifier = Modifier
                .background(PurpleGrey80)
                .fillMaxWidth()
                .padding(8.dp),
            screenState = screenState
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.remote_database),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            color = PurpleGrey40
        )
        TreeView(
            modifier = Modifier
                .weight(1f)
                .background(Pink80)
                .fillMaxWidth()
                .padding(8.dp),
            nodes = screenState.databaseLines,
            selectedId = screenState.selectedRemoteId,
            onItemClick = { viewModel.databaseItemClick(it) }
        )
    }
}

@Composable
fun ButtonsBar(
    modifier: Modifier = Modifier,
    viewModel: MainScreenViewModel = viewModel(),
    screenState: ScreenState
) {
    var stringDialog: Pair<String, (String) -> Unit>? by remember { mutableStateOf(null) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.padding(4.dp), horizontalArrangement = Arrangement.spacedBy(4.dp)) {


            Button(
                onClick = {
                    stringDialog =
                        viewModel.getNewNodeName() to { stringValue -> viewModel.create(stringValue) }
                },
                enabled = isSelectedCacheItemEditable(screenState)
            ) {
                Text(stringResource(R.string.create))
            }

            Button(
                onClick = {
                    val editingItemValue =
                        screenState.cacheLines[screenState.selectedCacheId]?.value
                            ?: viewModel.getNewNodeName()
                    stringDialog =
                        editingItemValue to { stringValue -> viewModel.edit(stringValue) }
                },
                enabled = isSelectedCacheItemEditable(screenState)
            ) {
                Text(stringResource(R.string.edit))
            }

            Button(
                onClick = { viewModel.deleteSelected() },
                enabled = isSelectedCacheItemEditable(screenState)
            ) {
                Text(stringResource(R.string.delete))
            }
        }

        Row(modifier = Modifier.padding(4.dp), horizontalArrangement = Arrangement.spacedBy(4.dp)) {

            Button(
                onClick = { viewModel.apply() },
                enabled = screenState.cacheLines.isNotEmpty()
            ) {
                Text(stringResource(R.string.apply))
            }

            Button(
                onClick = { viewModel.loadSelected() },
                enabled = screenState.selectedRemoteId != null && screenState.databaseLines.isNotEmpty()
            ) {
                Text(stringResource(R.string.load))
            }
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Red,
                ),
                onClick = { viewModel.resetAll() }
            ) {
                Text(stringResource(R.string.reset))
            }
        }
    }

    if (stringDialog != null) {
        val value = stringDialog!!.first

        StringValueDialog(defaultValue = value, onDismiss = { stringDialog = null }, onConfirm = {
            stringDialog!!.second.invoke(it)
        })
    }
}

@Composable
fun TreeView(
    modifier: Modifier = Modifier.fillMaxSize(),
    nodes: List<NodeUi> = emptyList(),
    selectedId: String? = null,
    onItemClick: (String) -> Unit
) {
    LazyColumn(modifier = modifier) {

        itemsIndexed(nodes) { index, node ->
            val indent = "    ".repeat(node.depth)
            val decoration = if (node.deleted) TextDecoration.LineThrough else TextDecoration.None

            Text(
                text = "$indent * ${node.value}",
                textDecoration = decoration,
                modifier = Modifier
                    .background(if (node.id == selectedId) PurpleGrey80 else Color.Transparent)
                    .fillMaxWidth()
                    .clickable { onItemClick(node.id) }
            )
        }
    }
}

@Composable
fun StringValueDialog(
    defaultValue: String = "Value",
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var text by remember { mutableStateOf(defaultValue) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Enter Value") },
        text = {
            TextField(
                value = text,
                onValueChange = { text = it },
                placeholder = { Text(defaultValue) }
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(text)
                    onDismiss()
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

private fun isSelectedCacheItemEditable(screenState: ScreenState) : Boolean {
    val id = screenState.selectedCacheId
    val node = screenState.cacheLines[id]
    val locked = node?.deleted ?: true
    return !locked
}