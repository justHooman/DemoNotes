package vn.justhooman.demo.notes.repos

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.verify
import vn.justhooman.demo.notes.db.AppDb
import vn.justhooman.demo.notes.db.Note
import vn.justhooman.demo.notes.db.NotesDao
import vn.justhooman.demo.notes.models.INote
import vn.justhooman.demo.notes.models.INoteContent
import vn.justhooman.demo.notes.presenters.TimeProvider
import vn.justhooman.demo.notes.test.MockitoTest

class NoteRepoImplTest : MockitoTest() {

  @Mock
  private lateinit var appDb: AppDb
  @Mock
  private lateinit var notesDao: NotesDao
  @Mock
  private lateinit var timeProvider: TimeProvider

  private lateinit var repo: NoteRepo

  private val defaultDate = "20240707"

  @Before
  fun setup() {
    doReturn(defaultDate).`when`(timeProvider).currentDate()
    doReturn(notesDao).`when`(appDb).notes()
    repo = NoteRepoImpl(appDb, timeProvider)
  }

  @Test
  fun testAddShouldInsertDataWithCurrentDate() = runTest {
    val title = "title"
    val content = "content"
    val note: INoteContent = mock()
    doReturn(title).`when`(note).title()
    doReturn(content).`when`(note).content()
    val expected = Note(
      title = title,
      content = content,
      date = defaultDate
    )
    doReturn(1L).`when`(notesDao).insert(expected)

    val ret = repo.add(note)

    verify(notesDao).insert(expected)
    assertEquals(1L, ret)
  }

  @Test
  fun testUpdateShouldUpdateDataWithCurrentDate() = runTest {
    val title = "title"
    val content = "content"
    val noteId = 100L
    val oldDate = "randomString"
    val note: INote = mock()
    doReturn(title).`when`(note).title()
    doReturn(content).`when`(note).content()
    doReturn(oldDate).`when`(note).date()
    doReturn(noteId).`when`(note).noteId()
    val expected = Note(
      noteId = noteId,
      title = title,
      content = content,
      date = defaultDate
    )
    doReturn(1).`when`(notesDao).update(expected)

    val ret = repo.update(note)

    verify(notesDao).update(expected)
    assertEquals(1, ret)
  }
}