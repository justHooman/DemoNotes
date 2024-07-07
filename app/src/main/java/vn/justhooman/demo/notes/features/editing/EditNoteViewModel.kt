package vn.justhooman.demo.notes.features.editing

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import vn.justhooman.demo.notes.db.Note
import vn.justhooman.demo.notes.models.INote
import vn.justhooman.demo.notes.presenters.DispatchersOwner
import vn.justhooman.demo.notes.presenters.EditNoteHandler
import vn.justhooman.demo.notes.presenters.LoadNoteHandler
import vn.justhooman.demo.notes.repos.NoteRepo
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(
  private val dispatchersOwner: DispatchersOwner,
  private val noteRepo: NoteRepo,
) : ViewModel(), LoadNoteHandler, EditNoteHandler {
  override var noteId: Long? = null

  private val _backActionFlow = MutableSharedFlow<Boolean>()
  val backActionFlow: Flow<Boolean> = _backActionFlow

  override suspend fun loadNote(newNoteId: Long?) {
    newNoteId?.let { noteIDLong ->
      noteId = noteIDLong
      noteRepo.findById(noteIDLong)
        .flowOn(dispatchersOwner.default)
        .collect { loadedNote ->
          _noteContent.value = loadedNote.toEditableNote()
        }
    }
  }

  fun saveNote() {
    viewModelScope.launch(dispatchersOwner.default) {
      val updated = noteRepo.update(
        note = _noteContent.value
      )
      _backActionFlow.emit(updated > 0)
    }
  }

  @VisibleForTesting
  internal val _noteContent = MutableStateFlow(EditableNote())
  val noteContent = _noteContent.asStateFlow()

  override fun setTitle(value: String) {
    _noteContent.update {
      it.copy(title = value)
    }
  }

  override fun setContent(value: String) {
    _noteContent.update {
      it.copy(content = value)
    }
  }
}

internal fun Note.toEditableNote(): EditableNote {
  return EditableNote(
    noteId = noteId,
    title = title,
    content = content,
    date = date
  )
}

data class EditableNote(
  val noteId: Long = 0L,
  val title: String = "",
  val content: String = "",
  val date: String = "",
) : INote {
  override fun noteId(): Long = this.noteId

  override fun date(): String = this.date

  override fun title(): String {
    return this.title
  }

  override fun content(): String {
    return this.content
  }
}