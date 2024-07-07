package vn.justhooman.demo.notes.presenters

import java.text.DateFormat
import java.text.SimpleDateFormat


interface TimeProvider {
  fun currentDate(): String
}

class TimeProviderImpl : TimeProvider {
  private val dateFormat: DateFormat by lazy { SimpleDateFormat.getDateInstance() }
  override fun currentDate(): String {
    return dateFormat.format(System.currentTimeMillis())
  }
}