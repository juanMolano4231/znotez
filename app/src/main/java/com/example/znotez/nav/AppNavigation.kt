package com.example.znotez.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.znotez.data.group.GroupRepository
import com.example.znotez.screens.EditGroupScreen
import com.example.znotez.screens.EditNoteScreen
import com.example.znotez.screens.GroupsScreen
import com.example.znotez.screens.HomeScreen
import com.example.znotez.screens.NotesScreen
import com.example.znotez.screens.SplashScreen

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Home : Screen("home")
    object Groups : Screen("groups")
    object Notes : Screen("notes")
    object EditGroup : Screen("edit_group?groupId={groupId}") {
        fun createRoute(groupId: String? = null) = "edit_group?groupId=$groupId"
    }
    object EditNote : Screen("edit_note?noteId={noteId}") {
        fun createRoute(noteId: String? = null) = "edit_note?noteId=$noteId"
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(onNavigateToHome = { navController.navigate(Screen.Home.route) })
        }
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToGroups = { navController.navigate(Screen.Groups.route) },
                onNavigateToEditNote = { navController.navigate(Screen.EditNote.route) }
            )
        }
        composable(Screen.Groups.route) {
            GroupsScreen(
                onNavigateToHome = { navController.navigate(Screen.Home.route) },
                onNavigateToEditGroup = { navController.navigate(Screen.EditGroup.createRoute(null)) } ,
                onNavigateToEditNote = { navController.navigate(Screen.EditNote.route) },
                onNavigateToNotes = { navController.navigate(Screen.Notes.route) },
            )
        }
        composable(Screen.Notes.route) {
            NotesScreen(
                onNavigateToHome = { navController.navigate(Screen.Home.route) },
                onNavigateToGroups = { navController.navigate(Screen.Groups.route) },
                onNavigateToEditNote = { navController.navigate(Screen.EditNote.route) }
            )
        }
        composable(
            route = Screen.EditGroup.route,
            arguments = listOf(
                navArgument("groupId") {
                    type = NavType.LongType
                    defaultValue = -1L
                }
            )
        ) { backStackEntry ->


            val context = LocalContext.current
            val repository = GroupRepository(context)

            val groupId = backStackEntry.arguments?.getLong("groupId") ?: -1L

            EditGroupScreen(
                groupId = groupId,
                repository = repository,
                onSave = { navController.popBackStack() },
                onCancel = { navController.popBackStack() },
                onNavigateToHome = { navController.navigate(Screen.Home.route) },
                onNavigateToGroups = { navController.navigate(Screen.Groups.route) },
                onNavigateToEditNote = { noteId ->
                    navController.navigate("edit_note/$noteId")
                }
            )
        }
        composable(
            route = Screen.EditNote.route,
            arguments = listOf(navArgument("noteId") { type = NavType.StringType; nullable = true })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId")
            EditNoteScreen(
                noteId = noteId,
                onSave = { navController.popBackStack() },
                onCancel = { navController.popBackStack() },
                onNavigateToHome = { navController.navigate(Screen.Home.route) },
                onNavigateToGroups = { navController.navigate(Screen.Groups.route) },
                onNavigateToEditNote = { navController.navigate(Screen.EditNote.route) },
                onNavigateToNotes = { navController.navigate(Screen.Notes.route) },
            )
        }
    }
}