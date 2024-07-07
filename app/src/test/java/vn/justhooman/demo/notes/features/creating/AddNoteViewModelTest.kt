package vn.justhooman.demo.notes.features.creating

import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.verify
import vn.justhooman.demo.notes.presenters.DispatchersOwner
import vn.justhooman.demo.notes.repos.NoteRepo
import vn.justhooman.demo.notes.test.MockitoTest
import vn.justhooman.demo.notes.test.TestDispatcherOwner

class AddNoteViewModelTest : MockitoTest() {

  private val dispatchersOwner: DispatchersOwner = TestDispatcherOwner()

  @Mock
  private lateinit var noteRepo: NoteRepo

  private lateinit var viewModel: AddNoteViewModel

  @Before
  fun setup() {
    viewModel = AddNoteViewModel(dispatchersOwner, noteRepo)
  }

  @Test
  fun testSaveNote() = runTest {
    val expected = NoteContent(
      title = "title",
      content = "content"
    )
    viewModel._noteContent.value = expected
    doReturn(1).`when`(noteRepo).add(expected)

    viewModel.saveNote()

    verify(noteRepo).add(expected)
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
}