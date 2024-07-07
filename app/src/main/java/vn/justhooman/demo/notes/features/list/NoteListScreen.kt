package vn.justhooman.demo.notes.features.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import vn.justhooman.demo.notes.R
import vn.justhooman.demo.notes.presenters.LocalNavigationProvider
import vn.justhooman.demo.notes.presenters.Routes
import vn.justhooman.demo.notes.ui.widgets.Title

@Composable
fun NoteListScreen(
) {
  Box(
    modifier = Modifier.fillMaxSize()
  ) {
    Column {
      Title(text = stringResource(id = R.string.note_list_label))
      NoteListWidget()
    }
    CreateNoteButton()
  }
}

@Composable
fun BoxScope.CreateNoteButton(
) {
  val navController = LocalNavigationProvider.current
  OutlinedIconButton(
    modifier = Modifier
      .align(Alignment.BottomEnd)
      .padding(16.dp),
    onClick = {
      navController.navigate(Routes.ROUTE_ADD_NOTE)
    }
  ) {
    Icon(
      painter = painterResource(id = android.R.drawable.ic_input_add),
      contentDescription = "add note"
    )
  }
}

@Composable
fun NoteListWidget(
  listViewModel: NoteListViewModel = hiltViewModel(),
) {
  val navController = LocalNavigationProvider.current
  val notes = listViewModel.notes.collectAsLazyPagingItems()
  LazyColumn(
    modifier = Modifier.fillMaxSize()
  ) {
    if (notes.itemCount == 0) {
      item {
        Text(text = stringResource(R.string.empty_note_message))
      }
    }
    items(notes.itemCount) { idx ->
      notes[idx]?.let { note ->
        Card(onClick = { navController.navigate(Routes.viewNoteRoute(note.noteId)) }) {
          Text(
            text = note.title,
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.titleMedium
          )
          Text(
            text = note.date,
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.labelMedium,
          )
        }
      }
    }
  }
}
