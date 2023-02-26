package dev.techpleiades.tms.ui.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.techpleiades.tms.data.TmsRepository
import dev.techpleiades.tms.data.local.database.Task
import dev.techpleiades.tms.ui.edit.EditUiState.Success
import dev.techpleiades.tms.ui.edit.EditUiState.Error
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    private val tmsRepository: TmsRepository,
    private val uid: Long,
) : ViewModel() {

    val uiState : StateFlow<EditUiState> = tmsRepository
        .findByUid(uid = uid).map(::Success)
        .catch { Error(it) }
        .stateIn(viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            EditUiState.Loading)
}

sealed interface EditUiState {
    object Loading: EditUiState

    data class Error(val throwable: Throwable): EditUiState

    data class Success(val data: Task): EditUiState
}