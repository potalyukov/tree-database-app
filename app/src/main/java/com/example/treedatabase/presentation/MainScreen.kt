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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    modifier: Modifier = Modifier.fillMaxSize(),
    viewModel: MainScreenViewModel = viewModel()
) {
    val screenState = viewModel.screenState.collectAsState().value

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
            lines = listOf("*nodeA", "   nodeB", "   nodeC", "     nodeD", "     nodeE")
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
            lines = listOf("*nodeA", "   nodeB", "   nodeC", "     nodeD", "     nodeE")
        )
    }
}

@Composable
fun ButtonsBar(modifier: Modifier = Modifier, viewModel: MainScreenViewModel = viewModel()) {
    Column(modifier = modifier, verticalArrangement = Arrangement.Center) {
        Row(modifier = Modifier.padding(4.dp), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Button(onClick = { viewModel.Create() }) {
                Text(stringResource(R.string.create))
            }
            Button(onClick = { viewModel.Delete(0) }) {
                Text(stringResource(R.string.delete))
            }
            Button(onClick = { viewModel.Reset() }) {
                Text(stringResource(R.string.reset))
            }
            Button(onClick = { viewModel.Apply() }) {
                Text(stringResource(R.string.apply))
            }
        }
        Button(
            onClick = { viewModel.Load(0) },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(stringResource(R.string.load))
        }
    }
}

@Composable
fun TreeView(modifier: Modifier = Modifier.fillMaxSize(), lines: List<String> = emptyList()) {
    var selectedIndex by remember { mutableIntStateOf(-1) }

    LazyColumn(modifier = modifier) {
        itemsIndexed(lines) { index, line ->
            TreeItem(
                text = line,
                id = index,
                selected = index == selectedIndex,
                onItemClick = { id ->
                    selectedIndex = if (selectedIndex == id) -1 else id
                })
        }
    }
}

@Composable
fun TreeItem(text: String, id: Int, selected: Boolean, onItemClick: (Int) -> Unit) {
    Text(
        text = text,
        modifier = Modifier
            .background(if (selected) PurpleGrey80 else PurpleGrey40)
            .clickable { onItemClick(id) }
    )
}