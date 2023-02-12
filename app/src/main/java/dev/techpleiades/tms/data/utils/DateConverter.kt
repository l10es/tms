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

package dev.techpleiades.tms.data.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.LocalDateTime

/**
 * Provide date convert util.
 *
 * Currently support "Timestamp to LocalDateTime" and
 * "LocalDateTime" to "Timestamp"
 */
class DateConverter {

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun toLocalDateTime(date: String): LocalDateTime {
        return date.let { LocalDateTime.parse(date) }
    }

    @TypeConverter
    fun fromLocalDateTime(localDateTime: LocalDateTime?): String? {
        return localDateTime?.toString()
    }
}

///**
// * Provide task status convert util.
// *
// * Currently support "TaskState" to "String" and
// * "String" to "TaskState".
// */
//class TaskStateConverter {
//
//    @TypeConverter
//    fun toTaskState(taskStateString: String): TaskStates {
//        return TaskStates.valueOf(taskStateString)
//    }
//
//    @TypeConverter
//    fun fromTaskState(taskState: TaskStates): String {
//        return taskState.name
//    }
//}