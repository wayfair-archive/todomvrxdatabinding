package com.wayfair.todomvrxdatabinding.taskdetail

import com.airbnb.mvrx.Success
import com.airbnb.mvrx.test.MvRxTestRule
import com.airbnb.mvrx.withState
import com.wayfair.todomvrxdatabinding.data.Task
import com.wayfair.todomvrxdatabinding.data.TaskCommonInput
import com.wayfair.todomvrxdatabinding.taskcommon.TaskRepository
import com.wayfair.todomvrxdatabinding.test.TestTasksDao
import org.assertj.core.api.Assertions.assertThat
import org.junit.ClassRule
import org.junit.Test

class TaskDetailViewModelTest {
    private val testDao = TestTasksDao().apply {
        upsertTask(TASK_EXIST).subscribe()
    }

    private val viewModel = testViewModel()

    private fun testViewModel() = TaskDetailViewModel(
        repository = TaskRepository(testDao),
        initialState = TaskDetailState(
            input = TaskCommonInput(1),
            task = Success(TASK_EXIST)
        )
    )

    @Test
    fun `given that is on task status is changed`() {
        viewModel.onTaskStatusToggleClick()
        withState(viewModel) {
            assertThat(it.task()!!.complete).isTrue()
        }
        viewModel.onTaskStatusToggleClick()
        withState(viewModel) {
            assertThat(it.task()!!.complete).isFalse()
        }
    }

    @Test
    fun `given that is task is deleted`() {
        viewModel.onMenuDeleteTaskClick()

        withState(viewModel) {
            assertThat(it.taskDeleted).isTrue()
        }
    }

    companion object {
        private val TASK_EXIST = Task(
            id = 1,
            title = "Title",
            description = "Description"
        )

        @JvmField
        @ClassRule
        val mvrxTestRule = MvRxTestRule()
    }
}
