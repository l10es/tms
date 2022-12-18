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