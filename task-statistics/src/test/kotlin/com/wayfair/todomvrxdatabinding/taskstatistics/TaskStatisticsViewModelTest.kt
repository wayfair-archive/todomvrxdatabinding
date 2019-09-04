package com.wayfair.todomvrxdatabinding.taskstatistics

import com.airbnb.mvrx.test.MvRxTestRule
import com.airbnb.mvrx.withState
import com.wayfair.todomvrxdatabinding.data.Task
import com.wayfair.todomvrxdatabinding.data.db.TestTaskStatisticsDao
import org.assertj.core.api.Assertions.assertThat
import org.junit.ClassRule
import org.junit.Test

internal class TaskStatisticsViewModelTest {

    private val testStatisticsDao = TestTaskStatisticsDao()

    private val viewModel = TaskStatisticsViewModel(
        TaskStatisticsRepository(testStatisticsDao),
        TaskStatisticsState()
    )

    @Test
    fun `given a number of Tasks, should show stats`() {
        testStatisticsDao.saveTask(COMPLETE_TASK).subscribe()

        withState(viewModel) {
            assertThat(it.shouldShowStats).isTrue()
        }
    }

    @Test
    fun `given no Tasks, should not show stats`() {
        testStatisticsDao.deleteTasks().subscribe()

        withState(viewModel) {
            assertThat(it.shouldShowStats).isFalse()
        }
    }

    @Test
    fun `given a number of complete tasks, should show complete tasks stats`() {
        testStatisticsDao.deleteTasks().subscribe()
        testStatisticsDao.saveTask(COMPLETE_TASK).subscribe()

        withState(viewModel) {
            assertThat(it.numberCompleteTasks()).isEqualTo(1)
            assertThat(it.numberIncompleteTasks()).isEqualTo(0)
        }
    }

    @Test
    fun `given a number of incomplete tasks, should show incomplete tasks stats`() {
        testStatisticsDao.deleteTasks().subscribe()
        testStatisticsDao.saveTask(INCOMPLETE_TASK).subscribe()

        withState(viewModel) {
            assertThat(it.numberCompleteTasks()).isEqualTo(0)
            assertThat(it.numberIncompleteTasks()).isEqualTo(1)
        }
    }

    companion object {
        private val INCOMPLETE_TASK = Task(
            id = 1,
            title = "Incomplete Task Title",
            description = "Description Incomplete Task"
        )
        private val COMPLETE_TASK = Task(
            id = 1,
            title = "Complete Task Title",
            description = "Description Complete Task 2",
            complete = true
        )

        @JvmField
        @ClassRule
        val mvrxTestRule = MvRxTestRule()
    }
}