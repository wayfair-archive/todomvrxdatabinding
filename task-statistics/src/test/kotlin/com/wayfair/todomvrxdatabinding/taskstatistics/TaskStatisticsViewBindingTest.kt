package com.wayfair.todomvrxdatabinding.taskstatistics

import android.content.res.Resources
import android.view.View.GONE
import android.view.View.VISIBLE
import com.airbnb.mvrx.test.MvRxTestRule
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.wayfair.todomvrxdatabinding.NavigationViewBinding
import com.wayfair.todomvrxdatabinding.data.Task
import com.wayfair.todomvrxdatabinding.data.db.TestTaskStatisticsDao
import org.assertj.core.api.Assertions.assertThat
import org.junit.ClassRule
import org.junit.Test

class TaskStatisticsViewBindingTest {

    private val testStatisticsDao = TestTaskStatisticsDao()
    private val viewModel = TaskStatisticsViewModel(
        TaskStatisticsRepository(testStatisticsDao),
        TaskStatisticsState()
    )
    private val resources = mock<Resources> {
        on { getString(R.string.task_statistics_title) } doReturn "Statistics"
        on { getString(R.string.task_statistics_complete_tasks, 1) } doReturn "Completed tasks: 1"
        on { getString(R.string.task_statistics_incomplete_tasks, 1) } doReturn "Active tasks: 1"
        on { getString(R.string.task_statistics_no_tasks) } doReturn "You have no tasks."
    }
    private val testFragment = TaskStatisticsFragment()
    private val viewBinding = TaskStatisticsViewBinding(
        testFragment,
        viewModel,
        NavigationViewBinding(testFragment),
        resources
    )

    @Test
    fun `given there are no tasks, show the empty stats placeholder`() {
        testStatisticsDao.deleteTasks().subscribe()
        assertThat(viewBinding.emptyStatsPlaceholderVisibility).isEqualTo(VISIBLE)
    }

    @Test
    fun `given there are tasks, do not show the empty stats placeholder`() {
        testStatisticsDao.saveTask(COMPLETE_TASK).subscribe()
        assertThat(viewBinding.emptyStatsPlaceholderVisibility).isEqualTo(GONE)
    }

    @Test
    fun `given there is an incomplete task, show stats about it`() {
        testStatisticsDao.deleteTasks().subscribe()
        testStatisticsDao.saveTask(INCOMPLETE_TASK).subscribe()
        assertThat(viewBinding.incompleteTasksText).isEqualTo("Active tasks: 1")
    }

    @Test
    fun `given there is a complete task, show stats about it`() {
        testStatisticsDao.deleteTasks().subscribe()
        testStatisticsDao.saveTask(COMPLETE_TASK).subscribe()
        assertThat(viewBinding.completeTasksText).isEqualTo("Completed tasks: 1")
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
