package vn.justhooman.demo.notes.presenters

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import vn.justhooman.demo.notes.features.creating.AddNoteScreen
import vn.justhooman.demo.notes.features.details.NoteDetailsScreen
import vn.justhooman.demo.notes.features.editing.EditNoteScreen
import vn.justhooman.demo.notes.features.list.NoteListScreen

val LocalNavigationProvider = staticCompositionLocalOf<NavHostController> { error("No navigation host controller provided.") }

object Routes {
  const val ARG_NOTE_ID = "noteId"
  const val ROUTE_NOTES = "notes"
  const val ROUTE_ADD_NOTE = "notes/add"
  const val ROUTE_VIEW_NOTE = "notes/{$ARG_NOTE_ID}"
  fun viewNoteRoute(noteId: Long) = "notes/$noteId"

  const val ROUTE_EDIT_NOTE = "notes/{$ARG_NOTE_ID}/edit"
  fun editNoteRoute(noteId: Long) = "notes/$noteId/edit"
}

@Composable
fun NotesAppNavigation(navController: NavHostController) {
  CompositionLocalProvider(LocalNavigationProvider provides navController) {
    NavHost(
      navController = navController,
      startDestination = "notes",
      modifier = Modifier.fillMaxSize()
    ) {
      composable(
        Routes.ROUTE_NOTES,
      ) {
        NoteListScreen()
      }
      composable(Routes.ROUTE_ADD_NOTE) {
        AddNoteScreen()
      }
      composable(
        Routes.ROUTE_VIEW_NOTE,
        arguments = listOf(navArgument(Routes.ARG_NOTE_ID) { type = NavType.LongType })
      ) {
        val noteId = it.arguments?.getLong(Routes.ARG_NOTE_ID)
        NoteDetailsScreen(noteId = noteId)
      }
      composable(
        Routes.ROUTE_EDIT_NOTE,
        arguments = listOf(navArgument(Routes.ARG_NOTE_ID) { type = NavType.LongType })
      ) {
        val noteId = it.arguments?.getLong(Routes.ARG_NOTE_ID)
        EditNoteScreen(noteId = noteId)
      }
    }
  }
}