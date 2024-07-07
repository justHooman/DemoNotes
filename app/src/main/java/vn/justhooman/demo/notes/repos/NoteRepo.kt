package vn.justhooman.demo.notes.repos

import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow
import vn.justhooman.demo.notes.db.AppDb
import vn.justhooman.demo.notes.db.Note
import vn.justhooman.demo.notes.db.NotesDao
import vn.justhooman.demo.notes.models.INote
import vn.justhooman.demo.notes.models.INoteContent
import vn.justhooman.demo.notes.presenters.TimeProvider
import javax.inject.Inject

interface NoteRepo {
  fun getAll(): PagingSource<Int, Note>
  fun findById(noteId: Long): Flow<Note>
  suspend fun add(note: INoteContent): Long
  suspend fun update(note: INote): Int

  suspend fun delete(noteId: Long): Int
}

class NoteRepoImpl @Inject constructor(
  private val appDb: AppDb,
  private val timeProvider: TimeProvider,
) : NoteRepo {
  val notes: NotesDao by lazy {
    appDb.notes()
  }

  override fun getAll(): PagingSource<Int, Note> = notes.getAll()

  override fun findById(noteId: Long): Flow<Note> = notes.findById(noteId)

  override suspend fun add(note: INoteContent): Long = notes.insert(
    note = note.run {
      Note(
        title = title(),
        content = content(),
        date = timeProvider.currentDate(),
      )
    }
  )

  override suspend fun update(note: INote): Int = notes.update(
    note = note.run {
      Note(
        noteId = noteId(),
        title = title(),
        content = content(),
        date = timeProvider.currentDate(),
      )
    }
  )

  override suspend fun delete(noteId: Long): Int = notes.delete(noteId)
}
