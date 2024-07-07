package vn.justhooman.demo.notes.features.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import vn.justhooman.demo.notes.db.Note
import vn.justhooman.demo.notes.presenters.DispatchersOwner
import vn.justhooman.demo.notes.presenters.LoadNoteHandler
import vn.justhooman.demo.notes.repos.NoteRepo
import javax.inject.Inject

@HiltViewModel
class NoteDetailsViewModel @Inject constructor(
  private val dispatchersOwner: DispatchersOwner,
  private val noteRepo: NoteRepo,
) : ViewModel(), LoadNoteHandler {
  override var noteId: Long? = null

  private val _backActionFlow = MutableSharedFlow<Boolean>()
  val backActionFlow: Flow<Boolean> = _backActionFlow

  private val _note = MutableStateFlow<Note?>(null)
  val note = _note.asStateFlow()

  override suspend fun loadNote(newNoteId: Long?) {
    newNoteId?.let { noteIDLong ->
      noteId = noteIDLong
      noteRepo.findById(noteIDLong)
        .flowOn(dispatchersOwner.default)
        .collect {
          _note.value = it
        }
    }
  }

  fun deleteNote() {
    viewModelScope.launch(dispatchersOwner.default) {
      noteId?.let { noteIDLong ->
        val deleted = noteRepo.delete(noteIDLong)
        _backActionFlow.emit(deleted > 0)
      }
    }
  }
}