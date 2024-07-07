package vn.justhooman.demo.notes.models

interface INoteContent {
  fun title(): String
  fun content(): String
}

interface INote : INoteContent {
  fun noteId(): Long
  fun date(): String
}
