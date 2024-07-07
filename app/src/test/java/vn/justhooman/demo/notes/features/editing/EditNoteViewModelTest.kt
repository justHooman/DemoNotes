package vn.justhooman.demo.notes.features.editing

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

class EditNoteViewModelTest : MockitoTest() {
  private val dispatchersOwner: DispatchersOwner = TestDispatcherOwner()

  @Mock
  private lateinit var noteRepo: NoteRepo

  private lateinit var viewModel: EditNoteViewModel

  @Before
  fun setup() {
    viewModel = EditNoteViewModel(dispatchersOwner, noteRepo)
  }

  @Test
  fun testLoadNote() = runTest {
    val noteId = 100L
    val note = fakeNote(noteId)
    doReturn(flowOf(note)).`when`(noteRepo).findById(noteId)

    viewModel.loadNote(newNoteId = noteId)

    Assert.assertEquals(noteId, viewModel.noteId)
    val noteContent = viewModel.noteContent.value
    Assert.assertEquals(note.noteId, noteContent.noteId)
    Assert.assertEquals(note.title, noteContent.title)
    Assert.assertEquals(note.content, noteContent.content)
    Assert.assertEquals(note.date, noteContent.date)
  }

  @Test
  fun testSaveNote() = runTest {
    val noteId = 100L
    val note = fakeNote(noteId)
    val expected = note.toEditableNote()
    viewModel._noteContent.value = expected
    doReturn(1).`when`(noteRepo).update(expected)

    viewModel.saveNote()

    verify(noteRepo).update(expected)
  }

  @Test
  fun verifyUpdateTitleData() = runTest {
    val newValue = "newValue"

    viewModel.setTitle(newValue)

    Assert.assertEquals(newValue, viewModel.noteContent.value.title)
  }

  @Test
  fun verifyUpdateContentData() = runTest {
    val newValue = "newValue"

    viewModel.setContent(newValue)

    Assert.assertEquals(newValue, viewModel.noteContent.value.content)
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