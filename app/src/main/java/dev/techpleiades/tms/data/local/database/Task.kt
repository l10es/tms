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

package dev.techpleiades.tms.data.local.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import java.util.*

@Entity(tableName = "task")
data class Task(
    @ColumnInfo(name = "name")
    var name: String = "",

    @ColumnInfo(name = "description")
    var description: String = "",

    /**
     * Task status
     *
     * New, Start, WIP, Done, Archived
     */
    @ColumnInfo(name = "status")
    var status: String,

    @ColumnInfo(name = "start_at")
    val startAt: LocalDateTime?,

    @ColumnInfo(name = "end_at")
    val endAt: LocalDateTime?,

    @ColumnInfo(name = "created_at")
    val createAt: LocalDateTime,
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Long = 0L
}

@Dao
interface TmsDao {
    @Query("SELECT * FROM task ORDER BY uid DESC LIMIT 10")
    fun getTasks(): Flow<List<Task>>

    @Query("SELECT * FROM task WHERE name LIKE :name ")
    fun getTasksByName(name: String?): Flow<List<Task>>

    @Query("SELECT * FROM task WHERE uid = :uid")
    fun getTaskById(uid: Long): Flow<Task>

    @Insert
    suspend fun insertTask(item: Task)

    @Update
    suspend fun updateTask(item: Task)

    @Query("DELETE FROM task WHERE uid = :uid")
    suspend fun removeTask(uid: Long)
}
