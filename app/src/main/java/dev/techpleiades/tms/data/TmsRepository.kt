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

package dev.techpleiades.tms.data

import android.os.Build
import androidx.annotation.RequiresApi
import dev.techpleiades.tms.data.local.database.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import dev.techpleiades.tms.data.local.database.TmsDao
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject

interface TmsRepository {
    val tasks: Flow<List<Task>>

    suspend fun add(
        name: String,
        description: String,
        status: String,
        startAt: LocalDateTime?,
        endAt: LocalDateTime?
    )

    suspend fun remove(uid: Long)

    suspend fun update(task: Task)

    suspend fun findByName(name: String): Flow<List<Task>>
}

class DefaultTmsRepository @Inject constructor(
    private val tmsDao: TmsDao
) : TmsRepository {

    override val tasks: Flow<List<Task>> =
        tmsDao.getTasks()

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun add(
        name: String,
        description: String,
        status: String,
        startAt: LocalDateTime?,
        endAt: LocalDateTime?
    ) {
        tmsDao.insertTask(
            Task(
                name = name,
                description = description,
                status = status,
                startAt = startAt,
                endAt = endAt,
                createAt = LocalDateTime.now()
            ))
    }

    override suspend fun remove(uid: Long) {
        tmsDao.removeTask(uid = uid)
    }

    override suspend fun update(task: Task) {
        tmsDao.updateTask(item = task)
    }

    override suspend fun findByName(name: String): Flow<List<Task>> {
        return tmsDao.getTasksByName(name = "${name}%")
    }

}
