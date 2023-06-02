package com.akvelon.wallpaper.presentation.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun MainBottomNavigation(
    navController: NavController,
    isShowBottomNav: Boolean
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    AnimatedVisibility(visible = MainBottomNavItem.isMainRoute(currentRoute) && isShowBottomNav) {
        BottomNavigation {
            listOf(
                MainBottomNavItem.Generate,
                MainBottomNavItem.History,
                MainBottomNavItem.Settings
            ).forEach { tab ->
                val isSelected = currentRoute == tab.route
                val title = stringResource(id = tab.title)
                BottomNavigationItem(
                    icon = { Icon(imageVector = tab.icon, contentDescription = title) },
                    label = { Text(text = title, softWrap = false) },
                    selected = isSelected,
                    onClick = {
                        navController.navigate(tab.route) {
                            navController.graph.startDestinationRoute?.let {
                                popUpTo(it) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}