/*
 * Copyright (C) 2022 The Android Open Source Project
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

package dev.techpleiades.tms.ui.tms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import dev.techpleiades.tms.data.TmsRepository
import dev.techpleiades.tms.ui.tms.TmsUiState.Error
import dev.techpleiades.tms.ui.tms.TmsUiState.Loading
import dev.techpleiades.tms.ui.tms.TmsUiState.Success
import javax.inject.Inject

@HiltViewModel
class TmsViewModel @Inject constructor(
    private val tmsRepository: TmsRepository
) : ViewModel() {

    val uiState: StateFlow<TmsUiState> = tmsRepository
        .tmss.map(::Success)
        .catch { Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Loading)

    fun addTms(name: String) {
        viewModelScope.launch {
            tmsRepository.add(name)
        }
    }
}

sealed interface TmsUiState {
    object Loading : TmsUiState
    data class Error(val throwable: Throwable) : TmsUiState
    data class Success(val data: List<String>) : TmsUiState
}
