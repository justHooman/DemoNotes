package vn.justhooman.demo.notes.features.creating

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import vn.justhooman.demo.notes.models.INoteContent
import vn.justhooman.demo.notes.presenters.DispatchersOwner
import vn.justhooman.demo.notes.presenters.EditNoteHandler
import vn.justhooman.demo.notes.repos.NoteRepo
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
  private val dispatchersOwner: DispatchersOwner,
  private val noteRepo: NoteRepo,
) : ViewModel(), EditNoteHandler {

  private val _backActionFlow = MutableSharedFlow<Boolean>()
  val backActionFlow: Flow<Boolean> = _backActionFlow

  fun saveNote() {
    viewModelScope.launch(dispatchersOwner.default) {
      val added = noteRepo.add(
        note = _noteContent.value
      )
      _backActionFlow.emit(added > 0)
    }
  }

  @VisibleForTesting
  internal val _noteContent = MutableStateFlow(NoteContent())
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

data class NoteContent(
  val title: String = "",
  val content: String = "",
) : INoteContent {
  override fun title(): String {
    return this.title
  }

  override fun content(): String {
    return this.content
  }
}