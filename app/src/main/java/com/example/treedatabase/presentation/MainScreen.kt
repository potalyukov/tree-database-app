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
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.treedatabase.R
import com.example.treedatabase.presentation.ui.theme.Pink80
import com.example.treedatabase.presentation.ui.theme.PurpleGrey40
import com.example.treedatabase.presentation.ui.theme.PurpleGrey80

@Composable
fun MainScreen(
    modifier: Modifier,
    viewModel: MainScreenViewModel = viewModel()
) {
    val screenState by viewModel.screenState.collectAsState()

    Column(modifier = modifier.padding(8.dp)) {
        Text(
            text = stringResource(R.string.cached_elements),
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
                .padding(10.dp),
            lines = screenState.cacheLines,
            onItemClick = { viewModel.cacheItemClick(it) },
            selectedIndex = screenState.selectedCacheLine
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
                .padding(8.dp)
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
            lines = screenState.databaseLines,
            selectedIndex = screenState.selectedDatabaseLine,
            onItemClick = { viewModel.databaseItemClick(it) }
        )
    }
}

@Composable
fun ButtonsBar(modifier: Modifier = Modifier, viewModel: MainScreenViewModel = viewModel()) {
    Column(modifier = modifier, verticalArrangement = Arrangement.Center) {
        Row(modifier = Modifier.padding(4.dp), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Button(onClick = { viewModel.create() }) {
                Text(stringResource(R.string.create))
            }
            Button(onClick = { viewModel.delete(0) }) {
                Text(stringResource(R.string.delete))
            }
            Button(onClick = { viewModel.reset() }) {
                Text(stringResource(R.string.reset))
            }
            Button(onClick = { viewModel.apply() }) {
                Text(stringResource(R.string.apply))
            }
        }
        Button(
            onClick = { viewModel.load(0) },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(stringResource(R.string.load))
        }
    }
}

@Composable
fun TreeView(
    modifier: Modifier = Modifier.fillMaxSize(),
    lines: List<String> = emptyList(),
    selectedIndex: Int = -1,
    onItemClick: (Int) -> Unit
) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(lines) { index, line ->
            Text(
                text = line,
                modifier = Modifier
                    .background(if (index == selectedIndex) PurpleGrey80 else PurpleGrey40)
                    .clickable { onItemClick(index) }
            )
        }
    }
}