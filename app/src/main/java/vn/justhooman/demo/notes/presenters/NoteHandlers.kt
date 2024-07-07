package vn.justhooman.demo.notes.presenters

interface EditNoteHandler {
  fun setTitle(value: String)
  fun setContent(value: String)
}

interface LoadNoteHandler {
  var noteId: Long?
  suspend fun loadNote(newNoteId: Long?)
}

