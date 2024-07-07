package vn.justhooman.demo.notes.test

import org.junit.After
import org.junit.Before
import org.mockito.MockitoAnnotations

open class MockitoTest {
  lateinit var autoCloseable: AutoCloseable

  @Before
  fun openMockito() {
    autoCloseable = MockitoAnnotations.openMocks(this)
  }

  @After
  fun cleanupMockito() {
    autoCloseable.close()
  }
}