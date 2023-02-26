package dev.techpleiades.tms.ui.edit

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import dev.techpleiades.tms.R
import dev.techpleiades.tms.data.enums.TaskStates
import dev.techpleiades.tms.data.local.database.Task
import dev.techpleiades.tms.ui.theme.MyApplicationTheme
import java.time.LocalDateTime

@Composable
fun EditScreen(
    modifier: Modifier = Modifier,
    viewModel: EditViewModel = hiltViewModel()
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val item by produceState<EditUiState>(
        initialValue = EditUiState.Loading,
        key1 = lifecycle,
        key2 = viewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            viewModel.uiState.collect { value = it }
        }
    }

    if (item is EditUiState.Success) {
        EditScreen(task = (item as EditUiState.Success).data)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("NewApi")
@Composable
internal fun EditScreen(
    task: Task? = Task(
        name = "",
        description = "",
        status = TaskStates.New.toString(),
        startAt = null,
        endAt = null,
        createAt = LocalDateTime.now()),
    modifier: Modifier = Modifier,
) {
    var statusDropdownExpanded by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        // Task Name
        OutlinedTextField(
            value = task!!.name,
            onValueChange = { task.name = it },
            label = { Text(stringResource(id = R.string.enter_task_name)) }
        )

        // Description
        TextField(
            value = task.description,
            onValueChange = { task.description = it },
            label = { Text(stringResource(id = R.string.enter_task_description)) }
        )

        // Status
        DropdownMenu(
            expanded = statusDropdownExpanded,
            onDismissRequest = { statusDropdownExpanded = false }
        ) {
            DropdownMenuItem (
                text = { Text(TaskStates.New.toString()) },
                onClick = { task.status = TaskStates.New.toString() },
            )
            DropdownMenuItem (
                text = { Text(TaskStates.Start.toString()) },
                onClick = { task.status = TaskStates.Start.toString() },
            )
            DropdownMenuItem (
                text = { Text(TaskStates.WIP.toString()) },
                onClick = { task.status = TaskStates.WIP.toString() },
            )
            DropdownMenuItem (
                text = { Text(TaskStates.Done.toString()) },
                onClick = { task.status = TaskStates.Done.toString() },
            )
            DropdownMenuItem (
                text = { Text(TaskStates.Archived.toString()) },
                onClick = { task.status = TaskStates.Archived.toString() },
            )
        }
    }
}

@Preview
@Composable
fun EmptyEditScreenPreview() {
    MyApplicationTheme {
        EditScreen(
            task = null
        )
    }
}