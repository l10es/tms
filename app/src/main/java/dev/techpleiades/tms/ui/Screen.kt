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

package dev.techpleiades.tms.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import dev.techpleiades.tms.R

sealed class Screen(
    val route: String,
    @StringRes val resourceId: Int,
    val icon: ImageVector,
) {
    object Home : Screen(
        route = "home",
        resourceId = R.string.home,
        icon = Icons.Outlined.Home,
    )
    object MyPage : Screen(
        route = "my_page",
        resourceId = R.string.my_page,
        icon = Icons.Outlined.Person,
    )
}

/**
 * Screen list for bottom navigation
 *
 * Contains: Home, MyPage
 */
val bottomScreens = listOf(
    Screen.Home,
    Screen.MyPage,
)