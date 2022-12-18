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