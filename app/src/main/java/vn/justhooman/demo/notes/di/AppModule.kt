package vn.justhooman.demo.notes.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import vn.justhooman.demo.notes.db.AppDb
import vn.justhooman.demo.notes.presenters.DispatchersOwner
import vn.justhooman.demo.notes.presenters.DispatchersOwnerImpl
import vn.justhooman.demo.notes.presenters.TimeProvider
import vn.justhooman.demo.notes.presenters.TimeProviderImpl
import vn.justhooman.demo.notes.repos.NoteRepo
import vn.justhooman.demo.notes.repos.NoteRepoImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppProviderModule {
  @Singleton
  @Provides
  fun provideDatabase(@ApplicationContext context: Context): AppDb = Room.databaseBuilder(
    context.applicationContext,
    AppDb::class.java,
    "notes.db"
  ).build()

  @Provides
  @Singleton
  fun provideDispatchers(): DispatchersOwner = DispatchersOwnerImpl()

  @Provides
  @Singleton
  fun provideTimes(): TimeProvider = TimeProviderImpl()
}

@Module
@InstallIn(SingletonComponent::class)
interface AppBinderModule {
  @Binds
  @Singleton
  fun bindNoteRepo(repo: NoteRepoImpl): NoteRepo
}