package com.wayfair.todomvrxdatabinding.taskdetail

import android.content.res.Resources
import android.graphics.drawable.Drawable
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.test.MvRxTestRule
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

class TaskDetailViewBindingTest {

    private val testDao = TestTasksDao().apply {
        upsertTask(TASK_EXIST).subscribe()
    }
    private val resources = mock<Resources> {
        on { getString(R.string.task_detail_title) } doReturn "Task Details"
        on { getDrawable(R.drawable.task_detail_ic_edit) } doReturn mock()
    }
    private val fragment = TaskDetailFragment()
    private val viewModel = testViewModel()
    private val viewBinding = TaskDetailViewBinding(
        fragment,
        viewModel,
        NavigationViewBinding(fragment),
        resources
    )

    private fun testViewModel() = TaskDetailViewModel(
        repository = TaskRepository(testDao),
        initialState = TaskDetailState(
            input = TaskCommonInput(1),
            task = Success(TASK_EXIST)
        )
    )

    @Test
    fun `given view binding is initialised fab button should be there`() {
        assertThat(viewBinding.fabImage).isInstanceOf(Drawable::class.java)
    }

    @Test
    fun `given view binding is initialised title should be set up`() {
        assertThat(viewBinding.navigationViewBinding.toolbarTitle).isEqualTo("Task Details")
    }

    @Test
    fun `given that task is fetched the data should be right`() {
        assertThat(viewBinding.taskTitle).isEqualTo("Title")
        assertThat(viewBinding.taskDescription).isEqualTo("Description")
        assertThat(viewBinding.isTaskComplete).isFalse()
    }

    @Test
    fun `given that task click on checkbox should change to active or completed`() {
        assertThat(viewBinding.isTaskComplete).isFalse()
        viewBinding.taskCheckboxClickListener.onClick(mock())
        assertThat(viewBinding.isTaskComplete).isTrue()
        viewBinding.taskCheckboxClickListener.onClick(mock())
        assertThat(viewBinding.isTaskComplete).isFalse()
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
