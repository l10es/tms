package dev.techpleiades.tms.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.techpleiades.tms.data.TmsRepository
import dev.techpleiades.tms.data.local.database.Task
import dev.techpleiades.tms.ui.home.HomeUiState.Success
import dev.techpleiades.tms.ui.home.HomeUiState.Error
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val tmsRepository: TmsRepository
) : ViewModel() {

    val uiState : StateFlow<HomeUiState> = tmsRepository
        .tasks.map(::Success)
        .catch { Error(it) }
        .stateIn(viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            HomeUiState.Loading)
}


sealed interface HomeUiState {
    object Loading: HomeUiState
    data class Error(val throwable: Throwable): HomeUiState
    data class Success(val data: List<Task>): HomeUiState
}