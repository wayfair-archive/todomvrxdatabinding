package com.wayfair.todomvrxdatabinding.tasklist

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.view.View
import com.airbnb.mvrx.test.MvRxTestRule
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.wayfair.todomvrxdatabinding.NavigationViewBinding
import com.wayfair.todomvrxdatabinding.data.Task
import com.wayfair.todomvrxdatabinding.test.TestTasksDao
import org.assertj.core.api.Assertions.assertThat
import org.junit.ClassRule
import org.junit.Test

class TaskListViewBindingTest {

    private val testDao = TestTasksDao().apply {
        upsertTask(TASK_IN_DAO_1).subscribe()
        upsertTask(TASK_IN_DAO_2).subscribe()
        upsertTask(TASK_IN_DAO_3).subscribe()
    }
    private val resources = mock<Resources> {
        on { getString(R.string.app_name) } doReturn "ToDo MvRx Databinding"
        on { getDrawable(R.drawable.task_list_ic_add) } doReturn mock()
    }
    private val fragment = TaskListFragment()
    private val viewModel = testViewModel()
    private val viewBinding = TaskListViewBinding(
        fragment,
        viewModel,
        NavigationViewBinding(fragment),
        resources
    )

    private fun testViewModel() = TaskListViewModel(
        repository = TaskListRepository(testDao),
        initialState = TaskListState()
    )

    @Test
    fun `given view binding initialised the title should be right one`() {
        assertThat(viewBinding.navigationViewBinding.toolbarTitle).isEqualTo("ToDo MvRx Databinding")
    }

    @Test
    fun `given view binding initialised the fab button should be there`() {
        assertThat(viewBinding.fabImage).isInstanceOf(Drawable::class.java)
    }

    @Test
    fun `given view binding initialised adapter should be task adapter`() {
        assertThat(viewBinding.adapter).isInstanceOf(TaskAdapter::class.java)
    }

    @Test
    fun `given view binding initialised with tasks the list of items should match`() {
        assertThat(viewBinding.taskListItems[0].id).isEqualTo(TASK_IN_DAO_1.id)
        assertThat(viewBinding.taskListItems[0].title).isEqualTo(TASK_IN_DAO_1.title)
        assertThat(viewBinding.taskListItems[0].complete).isEqualTo(TASK_IN_DAO_1.complete)
        assertThat(viewBinding.taskListItems[1].id).isEqualTo(TASK_IN_DAO_2.id)
        assertThat(viewBinding.taskListItems[1].title).isEqualTo(TASK_IN_DAO_2.title)
        assertThat(viewBinding.taskListItems[1].complete).isEqualTo(TASK_IN_DAO_2.complete)
        assertThat(viewBinding.taskListItems[2].id).isEqualTo(TASK_IN_DAO_3.id)
        assertThat(viewBinding.taskListItems[2].title).isEqualTo(TASK_IN_DAO_3.title)
        assertThat(viewBinding.taskListItems[2].complete).isEqualTo(TASK_IN_DAO_3.complete)
    }

    @Test
    fun `given view binding initialised loading indicator should be invisible`() {
        assertThat(viewBinding.loadingIndicatorVisibility).isEqualTo(View.INVISIBLE)
    }

    @Test
    fun `given tasks count the empty tasks placeholder should visible or gone`() {
        assertThat(viewBinding.emptyTasksPlaceholderVisibility).isEqualTo(View.GONE)

        testDao.deleteAll().subscribe()

        assertThat(viewBinding.emptyTasksPlaceholderVisibility).isEqualTo(View.VISIBLE)
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

        @JvmField
        @ClassRule
        val mvrxTestRule = MvRxTestRule()
    }
}
