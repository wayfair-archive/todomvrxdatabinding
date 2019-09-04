package com.wayfair.todomvrxdatabinding.tasklist

import com.airbnb.mvrx.test.MvRxTestRule
import com.airbnb.mvrx.withState
import com.wayfair.todomvrxdatabinding.data.Task
import com.wayfair.todomvrxdatabinding.test.TestTasksDao
import org.assertj.core.api.Assertions.assertThat
import org.junit.ClassRule
import org.junit.Test

class TaskListViewModelTest {

    private val testDao = TestTasksDao().apply {
        upsertTask(TASK_IN_DAO_1).subscribe()
        upsertTask(TASK_IN_DAO_2).subscribe()
        upsertTask(TASK_IN_DAO_3).subscribe()
    }

    private val viewModel = testViewModel()

    private fun testViewModel() = TaskListViewModel(
        repository = TaskListRepository(testDao),
        initialState = TaskListState()
    )

    @Test
    fun `given task on position was clicked the id should be right`() {
        viewModel.onTaskClick(TASK_IN_DAO_2.toTaskItem())

        withState(viewModel) {
            assertThat(it.taskClickedId).isEqualTo(2)
        }
    }

    @Test
    fun `should delete tasks when clear all tasks menu is clicked`() {
        withState(viewModel) {
            assertThat(it.tasks()).isNotEmpty
        }

        viewModel.onMenuClearAllClick()

        withState(viewModel) {
            assertThat(it.tasks()).isEmpty()
        }
    }

    @Test
    fun `given task on position was toggled it should be marked as accordingly`() {
        viewModel.onTaskCheckboxClick(TASK_IN_DAO_3.toTaskItem())

        withState(viewModel) {
            assertThat(it.tasks()!![0].complete).isTrue()
            assertThat(it.tasks()!![1].complete).isFalse()
            assertThat(it.tasks()!![2].complete).isTrue()
        }

        viewModel.onTaskCheckboxClick(TASK_IN_DAO_1.toTaskItem())
        withState(viewModel) {
            assertThat(it.tasks()!![0].complete).isFalse()
            assertThat(it.tasks()!![1].complete).isFalse()
            assertThat(it.tasks()!![2].complete).isTrue()
        }
    }

    @Test
    fun `given that navigation to next screen happened clicked id should be cleared`() {
        viewModel.onNavigatedToTaskDetail()

        withState(viewModel) {
            assertThat(it.taskClickedId).isEqualTo(0)
        }
    }

    companion object {
        private val TASK_IN_DAO_1 = Task(
            id = 1,
            title = "Title 1",
            description = "Description 1",
            complete = true
        )
        private val TASK_IN_DAO_2 = Task(
            id = 2,
            title = "Title 2",
            description = "Description 2"
        )
        private val TASK_IN_DAO_3 = Task(
            id = 3,
            title = "Title 3",
            description = "Description 3"
        )

        private fun Task.toTaskItem() = TaskListItem(id, title, complete)

        @JvmField
        @ClassRule
        val mvrxTestRule = MvRxTestRule()
    }
}
