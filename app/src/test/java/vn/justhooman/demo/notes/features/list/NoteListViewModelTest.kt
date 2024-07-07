package vn.justhooman.demo.notes.features.list

import androidx.paging.testing.asPagingSourceFactory
import androidx.paging.testing.asSnapshot
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.kotlin.doReturn
import vn.justhooman.demo.notes.db.Note
import vn.justhooman.demo.notes.presenters.DispatchersOwner
import vn.justhooman.demo.notes.repos.NoteRepo
import vn.justhooman.demo.notes.test.MockitoTest
import vn.justhooman.demo.notes.test.TestDispatcherOwner

class NoteListViewModelTest : MockitoTest() {
  private val dispatchersOwner: DispatchersOwner = TestDispatcherOwner()
  @Mock
  private lateinit var noteRepo: NoteRepo

  private lateinit var viewModel: NoteListViewModel

  @Before
  fun setup() {
    viewModel = NoteListViewModel(dispatchersOwner, noteRepo)
  }

  @Test
  fun testNotesLoadFromNoteRepo() = runTest {
    val mockNotes = (0L..10L).map {
      Note(
        noteId = it,
        title = "$it",
        content = "$it",
        date = "$it",
      )
    }
    val pagingSource = mockNotes.asPagingSourceFactory().invoke()
    doReturn(pagingSource).`when`(noteRepo).getAll()

    val result = viewModel.notes

    val snapshot = result.asSnapshot()
    Assert.assertEquals(mockNotes, snapshot)
  }
}