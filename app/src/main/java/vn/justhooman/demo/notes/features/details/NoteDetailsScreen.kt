package vn.justhooman.demo.notes.features.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import vn.justhooman.demo.notes.R
import vn.justhooman.demo.notes.presenters.LocalNavigationProvider
import vn.justhooman.demo.notes.presenters.Routes
import vn.justhooman.demo.notes.ui.widgets.ConfirmDialog
import vn.justhooman.demo.notes.ui.widgets.GoBackActionHandler
import vn.justhooman.demo.notes.ui.widgets.NavigateBackButton
import vn.justhooman.demo.notes.ui.widgets.Title

@Composable
fun NoteDetailsScreen(
  noteId: Long?,
  viewModel: NoteDetailsViewModel = hiltViewModel(),
) {
  LaunchedEffect(key1 = noteId) {
    viewModel.loadNote(noteId)
  }
  Column {
    Title(text = stringResource(id = R.string.note_details_label))
    NoteDetails()
    ViewNoteButtons(noteId)
  }
}

@Composable
private fun ViewNoteButtons(noteId: Long?) {
  noteId ?: return
  val navController = LocalNavigationProvider.current
  Row {
    NavigateBackButton()
    OutlinedButton(
      onClick = {
        navController.navigate(Routes.editNoteRoute(noteId))
      }
    ) {
      Text(text = stringResource(R.string.edit))
    }
    DeleteNoteButton()
  }
}

@Composable
private fun DeleteNoteButton() {
  val viewModel: NoteDetailsViewModel = hiltViewModel()
  val openAlertDialog = remember { mutableStateOf(false) }
  when {
    openAlertDialog.value -> {
      ConfirmDeleteDialog(
        onDismissRequest = { openAlertDialog.value = false },
        onConfirmation = {
          openAlertDialog.value = false
          viewModel.deleteNote()
        },
      )
    }
  }

  OutlinedButton(
    onClick = {
      openAlertDialog.value = true
    }
  ) {
    Text(text = stringResource(R.string.delete))
  }
  GoBackActionHandler(sharedFlow = viewModel.backActionFlow)
}

@Composable
fun ConfirmDeleteDialog(
  onDismissRequest: () -> Unit,
  onConfirmation: () -> Unit,
) {
  ConfirmDialog(
    dialogText = stringResource(R.string.confirm_to_delete_note),
    onDismissRequest = onDismissRequest,
    onConfirmation = onConfirmation
  )
}

@Composable
fun ColumnScope.NoteDetails(
  viewModel: NoteDetailsViewModel = hiltViewModel()
) {
  val note by viewModel.note.collectAsStateWithLifecycle()
  Text(
    text = "${note?.title}",
    modifier = Modifier.fillMaxWidth(),
    style = MaterialTheme.typography.titleMedium
  )
  Text(
    text = "${note?.date}",
    modifier = Modifier.fillMaxWidth(),
    style = MaterialTheme.typography.labelMedium,
  )
  Text(
    text = "${note?.content}",
    modifier = Modifier
      .fillMaxWidth()
      .weight(1f, fill = true),
    style = MaterialTheme.typography.bodyMedium,
  )
}
