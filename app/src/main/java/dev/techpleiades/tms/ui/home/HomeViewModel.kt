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