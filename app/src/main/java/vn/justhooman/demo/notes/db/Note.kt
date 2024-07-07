package vn.justhooman.demo.notes.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

internal const val TABLE_NAME_NOTES = "notes"

@Entity(
  tableName = TABLE_NAME_NOTES,
)
data class Note(
  @PrimaryKey(autoGenerate = true)
  val noteId: Long = 0L,
  val title: String,
  val content: String,
  val date: String,
)

@Dao
interface NotesDao {
  @Insert(entity = Note::class, onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(note: Note): Long

  @Update(onConflict = OnConflictStrategy.REPLACE)
  suspend fun update(note: Note): Int

  @Query("SELECT * FROM $TABLE_NAME_NOTES")
  fun getAll(): PagingSource<Int, Note>

  @Query("SELECT * FROM $TABLE_NAME_NOTES WHERE noteId=:noteId ")
  fun findById(noteId: Long): Flow<Note>

  @Query("DELETE FROM $TABLE_NAME_NOTES WHERE noteId=:noteId ")
  fun delete(noteId: Long): Int
}