package vn.justhooman.demo.notes.features.details

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.verify
import vn.justhooman.demo.notes.db.Note
import vn.justhooman.demo.notes.presenters.DispatchersOwner
import vn.justhooman.demo.notes.repos.NoteRepo
import vn.justhooman.demo.notes.test.MockitoTest
import vn.justhooman.demo.notes.test.TestDispatcherOwner


class NoteDetailsViewModelTest : MockitoTest() {

  private val dispatchersOwner: DispatchersOwner = TestDispatcherOwner()

  @Mock
  private lateinit var noteRepo: NoteRepo

  private lateinit var viewModel: NoteDetailsViewModel

  @Before
  fun setup() {
    viewModel = NoteDetailsViewModel(dispatchersOwner, noteRepo)
  }

  @Test
  fun testLoadNote() = runTest {
    val noteId = 100L
    val note = fakeNote(noteId)
    doReturn(flowOf(note)).`when`(noteRepo).findById(noteId)

    viewModel.loadNote(newNoteId = noteId)

    Assert.assertEquals(noteId, viewModel.noteId)
    val noteContent = viewModel.note.value
    Assert.assertEquals(note.noteId, noteContent?.noteId)
    Assert.assertEquals(note.title, noteContent?.title)
    Assert.assertEquals(note.content, noteContent?.content)
    Assert.assertEquals(note.date, noteContent?.date)
  }

  @Test
  fun testDeleteNote() = runTest {
    val noteId = 100L
    doReturn(1).`when`(noteRepo).delete(noteId)
    viewModel.noteId = noteId

    viewModel.deleteNote()

    verify(noteRepo).delete(noteId)
  }

  private fun fakeNote(noteId: Long) = noteId.let {
    Note(
      noteId = it,
      title = "$it",
      content = "$it",
      date = "$it",
    )
  }
}