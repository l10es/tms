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

package dev.techpleiades.tms.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import dev.techpleiades.tms.data.local.database.Tms
import dev.techpleiades.tms.data.local.database.TmsDao
import javax.inject.Inject

interface TmsRepository {
    val tmss: Flow<List<String>>

    suspend fun add(name: String)
}

class DefaultTmsRepository @Inject constructor(
    private val tmsDao: TmsDao
) : TmsRepository {

    override val tmss: Flow<List<String>> =
        tmsDao.getTmss().map { items -> items.map { it.name } }

    override suspend fun add(name: String) {
        tmsDao.insertTms(Tms(name = name))
    }
}
