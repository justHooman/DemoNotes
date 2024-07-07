package vn.justhooman.demo.notes.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
  entities = [
    Note::class,
  ], version = 1
)
abstract class AppDb : RoomDatabase() {
  abstract fun notes(): NotesDao
}