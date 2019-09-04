package com.wayfair.todomvrxdatabinding.taskaddedit

import android.content.res.Resources
import android.graphics.drawable.Drawable
import com.airbnb.mvrx.test.MvRxTestRule
import com.airbnb.mvrx.withState
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.wayfair.todomvrxdatabinding.NavigationViewBinding
import com.wayfair.todomvrxdatabinding.data.Task
import com.wayfair.todomvrxdatabinding.data.TaskCommonInput
import com.wayfair.todomvrxdatabinding.taskcommon.TaskRepository
import com.wayfair.todomvrxdatabinding.test.TestTasksDao
import org.assertj.core.api.Assertions.assertThat
import org.junit.ClassRule
import org.junit.Test

class TaskAddEditViewBindingTest {

    private val testDao = TestTasksDao().apply {
        upsertTask(TASK_IN_DAO).subscribe()
    }
    private val resources = mock<Resources> {
        on { getString(R.string.task_add_edit_title) } doReturn "Add/Edit Task"
        on { getDrawable(R.drawable.task_add_edit_ic_done) } doReturn mock()
    }
    private val fragment = TaskAddEditFragment()
    private val viewModel = testViewModel()
    private val taskAddEditViewBinding = TaskAddEditViewBinding(
        fragment,
        viewModel,
        NavigationViewBinding(fragment),
        resources
    )

    @Test
    fun `given that view is attached the title should be right one`() {
        assertThat(taskAddEditViewBinding.navigationViewBinding.toolbarTitle).isEqualTo("Add/Edit Task")
    }

    @Test
    fun `given that view is attached fab image should be defined`() {
        assertThat(taskAddEditViewBinding.fabImage).isInstanceOf(Drawable::class.java)
    }

    @Test
    fun `given that task is fetched the title should be right`() {
        assertThat(taskAddEditViewBinding.taskTitle).isEqualTo("Title")
    }

    @Test
    fun `given that task title is updated the title should be the new one`() {
        taskAddEditViewBinding.taskTitle = "New Title"
        withState(viewModel) {
            assertThat(it.task()!!.title).isEqualTo("New Title")
        }
    }

    @Test
    fun `given that task is fetched the description should be right`() {
        assertThat(taskAddEditViewBinding.taskDescription).isEqualTo("Description")
    }

    @Test
    fun `given that task description is updated the description should be the new one`() {
        taskAddEditViewBinding.taskDescription = "New Description"
        withState(viewModel) {
            assertThat(it.task()!!.description).isEqualTo("New Description")
        }
    }

    private fun testViewModel() = TaskAddEditViewModel(
        repository = TaskRepository(testDao),
        initialState = TaskAddEditState(
            input = TaskCommonInput(TASK_IN_DAO.id),
            isTaskSuccessfullySaved = false
        )
    )

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
