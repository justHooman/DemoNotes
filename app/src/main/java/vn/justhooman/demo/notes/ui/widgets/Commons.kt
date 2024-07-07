package vn.justhooman.demo.notes.ui.widgets

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import kotlinx.coroutines.flow.Flow
import vn.justhooman.demo.notes.R
import vn.justhooman.demo.notes.models.INoteContent
import vn.justhooman.demo.notes.presenters.EditNoteHandler
import vn.justhooman.demo.notes.presenters.LocalNavigationProvider

@Composable
fun Title(text: String) {
  Text(
    text = text,
    style = MaterialTheme.typography.titleMedium,
  )
}

@Composable
fun NavigateBackButton() {
  val navController = LocalNavigationProvider.current
  OutlinedButton(
    onClick = {
      navController.popBackStack()
    }
  ) {
    Text(text = stringResource(R.string.back))
  }
}

@Composable
fun GoBackActionHandler(sharedFlow: Flow<Boolean>) {
  val navController = LocalNavigationProvider.current
  LaunchedEffect(Unit) {
    sharedFlow.collect {
      if (it) navController.popBackStack()
    }
  }
}

@Composable
fun ConfirmDialog(
  dialogText: String,
  onDismissRequest: () -> Unit,
  onConfirmation: () -> Unit,
) {
  AlertDialog(
    title = {
      Text(text = stringResource(R.string.confirm_dialog_title))
    },
    text = {
      Text(text = dialogText)
    },
    onDismissRequest = {
      onDismissRequest()
    },
    confirmButton = {
      OutlinedButton(
        onClick = onConfirmation
      ) {
        Text(stringResource(R.string.confirm))
      }
    },
    dismissButton = {
      OutlinedButton(
        onClick = onDismissRequest
      ) {
        Text(stringResource(R.string.dismiss))
      }
    }
  )
}

@Composable
fun NoteTitleTextField(
  noteContent: INoteContent,
  handler: EditNoteHandler
) {
  OutlinedTextField(
    value = noteContent.title(),
    onValueChange = { newValue -> handler.setTitle(newValue) },
    modifier = Modifier.fillMaxWidth(),
    placeholder = {
      Text(text = stringResource(R.string.add_note_title_hint))
    }
  )
}

@Composable
fun ColumnScope.NoteContentTextField(
  noteContent: INoteContent,
  handler: EditNoteHandler
) {
  OutlinedTextField(
    value = noteContent.content(),
    onValueChange = { newValue -> handler.setContent(newValue) },
    modifier = Modifier
      .fillMaxWidth()
      .weight(1F, fill = true),
    placeholder = {
      Text(text = stringResource(R.string.add_note_content_hint))
    },
    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
  )
}