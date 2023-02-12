/*
 * Copyright (C) 2023 TMS Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.techpleiades.tms.ui.home

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import dev.techpleiades.tms.R
import dev.techpleiades.tms.data.di.fakeTasks
import dev.techpleiades.tms.data.local.database.Task
import dev.techpleiades.tms.ui.theme.MyApplicationTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val items by produceState<HomeUiState>(
        initialValue = HomeUiState.Loading,
        key1 = lifecycle,
        key2 = viewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            viewModel.uiState.collect { value = it }
        }
    }
    if (items is HomeUiState.Success) {
        HomeScreen(
            tasks = (items as HomeUiState.Success).data,
        )
    }
}

@SuppressLint("NewApi")
@Composable
internal fun HomeScreen(
    tasks: List<Task>,
    modifier: Modifier = Modifier,
) {

    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.welcome),
            style = MaterialTheme.typography.titleLarge,
            modifier = modifier.padding(16.dp),
        )

        if (tasks.isEmpty()) {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    modifier = modifier.padding(16.dp),
                    text = stringResource(id = R.string.all_done)
                )
            }
        } else {
            Column {
                Text(
                    text = stringResource(id = R.string.current_tasks),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = modifier.padding(10.dp),
                )
                LazyColumn {
                    items(tasks) { task ->
                        Card(
                            modifier = modifier
                                .weight(1F)
                                .padding(6.dp),
                            content = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                ) {
                                    Column {
                                        Text(
                                            text = task.name,
                                            style = MaterialTheme.typography.labelLarge,
                                        )
                                        Text(
                                            text = task.status,
                                            style = MaterialTheme.typography.labelSmall
                                        )
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun EmptyListPreview() {
    MyApplicationTheme {
        HomeScreen(tasks = emptyList())
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun ListPreview() {
    MyApplicationTheme {
        HomeScreen(
            tasks = fakeTasks,
        )
    }
}