package vn.justhooman.demo.notes.test

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import vn.justhooman.demo.notes.presenters.DispatchersOwner

class TestDispatcherOwner(
  private val dispatcher: CoroutineDispatcher = UnconfinedTestDispatcher(),
  override val default: CoroutineDispatcher = dispatcher,
  override val main: CoroutineDispatcher = dispatcher,
  override val unconfirmed: CoroutineDispatcher = dispatcher,
  override val io: CoroutineDispatcher = dispatcher
) : DispatchersOwner {

}