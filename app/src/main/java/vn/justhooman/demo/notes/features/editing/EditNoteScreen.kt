package vn.justhooman.demo.notes.features.editing

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import vn.justhooman.demo.notes.R
import vn.justhooman.demo.notes.ui.widgets.GoBackActionHandler
import vn.justhooman.demo.notes.ui.widgets.NavigateBackButton
import vn.justhooman.demo.notes.ui.widgets.NoteContentTextField
import vn.justhooman.demo.notes.ui.widgets.NoteTitleTextField
import vn.justhooman.demo.notes.ui.widgets.Title

@Composable
fun EditNoteScreen(
  noteId: Long?,
  viewModel: EditNoteViewModel = hiltViewModel(),
) {
  LaunchedEffect(key1 = noteId) {
    viewModel.loadNote(noteId)
  }
  Column {
    Title(text = stringResource(id = R.string.edit_note_label))
    EditNote()
    Row {
      NavigateBackButton()
      OutlinedButton(
        onClick = viewModel::saveNote
      ) {
        Text(text = stringResource(R.string.save))
      }
      GoBackActionHandler(sharedFlow = viewModel.backActionFlow)
    }
  }
}

@Composable
fun ColumnScope.EditNote(
  viewModel: EditNoteViewModel = hiltViewModel()
) {
  val noteContent by viewModel.noteContent.collectAsStateWithLifecycle()
//  TextField(
//    value = noteContent.title(),
//    onValueChange = { newValue -> viewModel.setTitle(newValue) },
//    modifier = Modifier.fillMaxWidth(),
//    placeholder = {
//      Text(text = stringResource(R.string.add_note_title_hint))
//    }
//  )
//  TextField(
//    value = noteContent.content(),
//    onValueChange = { newValue -> viewModel.setContent(newValue) },
//    modifier = Modifier
//      .fillMaxWidth()
//      .weight(1F, fill = true),
//    placeholder = {
//      Text(text = stringResource(R.string.add_note_content_hint))
//    }
//  )

  NoteTitleTextField(noteContent, viewModel)
  Text(
    text = noteContent.date,
    modifier = Modifier.fillMaxWidth()
  )
  NoteContentTextField(noteContent, viewModel)
}