package com.example.treedatabase.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MainScreen(modifier: Modifier = Modifier.fillMaxSize(), viewModel: MainScreenViewModel = viewModel()) =
    Column(modifier = modifier) {
        val screenState = viewModel.screenState.collectAsState().value

        Column {
            TreeView(listOf("nodeA","-nodeB","-nodeC","--nodeD","--nodeE"))
            Spacer(modifier = Modifier.height(10.dp))
            TreeView(listOf("nodeA","-nodeB","-nodeC","--nodeD","--nodeE"))
        }
    }

@Composable
fun TreeView(lines: List<String> = emptyList()) {
    LazyColumn {
        items(lines) { line ->
            Text(text = line)
        }
    }
}