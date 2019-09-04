package com.wayfair.todomvrxdatabinding.taskaddedit

import com.airbnb.mvrx.test.MvRxTestRule
import com.airbnb.mvrx.withState
import com.wayfair.todomvrxdatabinding.data.Task
import com.wayfair.todomvrxdatabinding.data.TaskCommonInput
import com.wayfair.todomvrxdatabinding.taskcommon.TaskRepository
import com.wayfair.todomvrxdatabinding.test.TestTasksDao
import org.assertj.core.api.Assertions.assertThat
import org.junit.ClassRule
import org.junit.Test

internal class TaskAddEditViewModelTest {

    private val testDao = TestTasksDao()

    init {
        testDao.upsertTask(TASK_IN_DAO).subscribe()
    }

    private val taskAddEditViewModelAdd = testViewModel(newTask = true)
    private val taskAddEditViewModelEdit = testViewModel(newTask = false)

    private fun testViewModel(newTask: Boolean) = TaskAddEditViewModel(
        repository = TaskRepository(testDao),
        initialState = testInitState(newTask)
    )

    private fun testInitState(newTask: Boolean) = if (newTask) {
        TaskAddEditState(input = null)
    } else {
        TaskAddEditState(input = TaskCommonInput(TASK_IN_DAO.id))
    }

    @Test
    fun `given add new task initial state task id should be zero`() {
        withState(taskAddEditViewModelAdd) {
            assertThat(it.task()!!.id).isEqualTo(0)
        }
    }

    @Test
    fun `given edit task initial state task id should bigger than zero`() {
        withState(taskAddEditViewModelEdit) {
            assertThat(it.task()!!.id).isGreaterThan(0)
        }
    }

    @Test
    fun `given title is not empty should save task`() {
        taskAddEditViewModelAdd.onUpdateTaskTitle("not empty")
        taskAddEditViewModelAdd.onSaveTask()
        withState(taskAddEditViewModelAdd) {
            assertThat(it.isTaskSuccessfullySaved).isTrue()
        }
    }

    @Test
    fun `given title is empty should save task`() {
        taskAddEditViewModelAdd.onUpdateTaskTitle("")
        taskAddEditViewModelAdd.onSaveTask()
        withState(taskAddEditViewModelAdd) {
            assertThat(it.isTaskSuccessfullySaved).isFalse()
        }
    }

    @Test
    fun `given new title state should be updated`() {
        taskAddEditViewModelAdd.onUpdateTaskTitle("new title")
        withState(taskAddEditViewModelAdd) {
            assertThat(it.task()!!.title).isEqualTo("new title")
        }
    }

    @Test
    fun `given new description state should be updated`() {
        taskAddEditViewModelAdd.onUpdateTaskDescription("new description")
        withState(taskAddEditViewModelAdd) {
            assertThat(it.task()!!.description).isEqualTo("new description")
        }
    }

    companion object {
        private val TASK_IN_DAO = Task(
            id = 1,
            title = "Title",
            description = "Description"
        )

        @JvmField
        @ClassRule
        val mvrxTestRule = MvRxTestRule()
    }
}
