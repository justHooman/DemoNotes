package vn.justhooman.demo.notes.presenters

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface DispatchersOwner {
  val default: CoroutineDispatcher
  val main: CoroutineDispatcher
  val unconfirmed: CoroutineDispatcher
  val io: CoroutineDispatcher
}

class DispatchersOwnerImpl : DispatchersOwner {
  override val default: CoroutineDispatcher
    get() = Dispatchers.Default
  override val main: CoroutineDispatcher
    get() = Dispatchers.Main
  override val unconfirmed: CoroutineDispatcher
    get() = Dispatchers.Unconfined
  override val io: CoroutineDispatcher
    get() = Dispatchers.IO
}