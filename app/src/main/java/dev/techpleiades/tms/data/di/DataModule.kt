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

package dev.techpleiades.tms.data.di

import android.os.Build
import androidx.annotation.RequiresApi
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.techpleiades.tms.data.DefaultTmsRepository
import dev.techpleiades.tms.data.TmsRepository
import dev.techpleiades.tms.data.local.database.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindsTmsRepository(
        tmsRepository: DefaultTmsRepository
    ): TmsRepository
}

class FakeTmsRepository @Inject constructor() : TmsRepository {
    override val tasks: Flow<List<Task>>
        @RequiresApi(Build.VERSION_CODES.O)
        get() = flowOf(fakeTasks)

    override suspend fun add(
        name: String,
        description: String,
        status: String,
        startAt: LocalDateTime?,
        endAt: LocalDateTime?
    ) {
        throw NotImplementedError()
    }

    override suspend fun remove(uid: Long) {
        throw NotImplementedError()
    }

    override suspend fun update(task: Task) {
        throw NotImplementedError()
    }

    override suspend fun findByName(name: String): Flow<List<Task>> {
        throw NotImplementedError()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
val fakeTasks = listOf(
    Task(
        "sample1",
        "sample",
        "New",
        null,
        null,
        LocalDateTime.now()
    ),
    Task(
        "sample2",
        "sample2",
        "WIP",
        null,
        null,
        LocalDateTime.now()
    ),
    Task(
        "sample3",
        "sample3",
        "Start",
        null,
        null,
        LocalDateTime.now()
    ),
    Task(
        "sample4",
        "sample4",
        "Start",
        null,
        null,
        LocalDateTime.now()
    ),
    Task(
        "sample5",
        "sample5",
        "Start",
        null,
        null,
        LocalDateTime.now()
    ),
    Task(
        "sample6",
        "sample7",
        "Start",
        null,
        null,
        LocalDateTime.now()
    ),
    Task(
        "sample9",
        "sample9",
        "Start",
        null,
        null,
        LocalDateTime.now()
    ),
    Task(
        "sample10",
        "sample10",
        "Start",
        null,
        null,
        LocalDateTime.now()
    ),
    Task(
        "sample11",
        "sample11",
        "Start",
        null,
        null,
        LocalDateTime.now()
    ),
    Task(
        "sample12",
        "sample12",
        "Start",
        null,
        null,
        LocalDateTime.now()
    ),
    Task(
        "sample13",
        "sample13",
        "Start",
        null,
        null,
        LocalDateTime.now()
    ),
    Task(
        "sample14",
        "sample14",
        "Start",
        null,
        null,
        LocalDateTime.now()
    ),
    Task(
        "sample15",
        "sample15",
        "Start",
        null,
        null,
        LocalDateTime.now()
    ),
)
