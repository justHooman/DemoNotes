package vn.justhooman.demo.notes.features.list

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import vn.justhooman.demo.notes.db.Note
import vn.justhooman.demo.notes.presenters.DispatchersOwner
import vn.justhooman.demo.notes.repos.NoteRepo
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
  private val dispatchersOwner: DispatchersOwner,
  private val noteRepo: NoteRepo,
) : ViewModel() {

  val notes: Flow<PagingData<Note>> by lazy {
    Pager(
      config = PagingConfig(pageSize = 20),
      initialKey = null,
      pagingSourceFactory = { noteRepo.getAll() }
    ).flow
      .flowOn(dispatchersOwner.default)
  }
}