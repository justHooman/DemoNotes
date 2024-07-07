package vn.justhooman.demo.notes.features.creating

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun AddNoteScreen() {
  Column(
  ) {
    Title(text = stringResource(id = R.string.add_note_label))
    EditNote()
    AddNoteButtons()
  }
}

@Composable
private fun AddNoteButtons() {
  Row(
    modifier = Modifier.fillMaxWidth()
  ) {
    NavigateBackButton()
    val viewModel: AddNoteViewModel = hiltViewModel()
    OutlinedButton(
      onClick = viewModel::saveNote,
    ) {
      Text(text = stringResource(R.string.save))
    }
    GoBackActionHandler(sharedFlow = viewModel.backActionFlow)
  }
}

@Composable
fun ColumnScope.EditNote(
  viewModel: AddNoteViewModel = hiltViewModel()
) {
  val noteContent by viewModel.noteContent.collectAsStateWithLifecycle()
  NoteTitleTextField(noteContent, viewModel)
  NoteContentTextField(noteContent, viewModel)
}

