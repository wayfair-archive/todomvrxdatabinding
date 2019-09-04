package com.wayfair.todomvrxdatabinding.tasklist

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.OnClickListener
import android.view.View.VISIBLE
import androidx.appcompat.widget.Toolbar
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.withState
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import com.wayfair.todomvrxdatabinding.BaseViewBinding
import com.wayfair.todomvrxdatabinding.NavigationViewBinding
import com.wayfair.todomvrxdatabinding.data.TaskCommonInput
import com.wayfair.todomvrxdatabinding.ktx.navigateTo

internal class TaskListViewBinding @AssistedInject constructor(
    fragment: TaskListFragment,
    @Assisted override val viewModel: TaskListViewModel,
    val navigationViewBinding: NavigationViewBinding,
    resources: Resources
) : BaseViewBinding<TaskListState>(fragment, viewModel) {

    val adapter = TaskAdapter(viewModel)

    val taskListItems: List<TaskListItem>
        get() = withState(viewModel) {
            it.tasks().orEmpty()
        }

    val loadingIndicatorVisibility: Int
        get() = withState(viewModel) {
            if (it.tasks is Loading) VISIBLE else INVISIBLE
        }

    val emptyTasksPlaceholderVisibility: Int
        get() = withState(viewModel) {
            if (it.tasks.complete && it.tasks()!!.isEmpty()) VISIBLE else GONE
        }

    val onFabClickListener = OnClickListener {
        findNavController().navigate(R.id.addEditFragment)
    }

    val fabImage: Drawable = resources.getDrawable(R.drawable.task_list_ic_add)!!

    init {
        navigationViewBinding.toolbarTitle = resources.getString(R.string.app_name)
        navigationViewBinding.toolbarMenuResId = R.menu.task_list_menu
        navigationViewBinding.menuItemClickListener = Toolbar.OnMenuItemClickListener {
            viewModel.onMenuClearAllClick()
            true
        }
        subscribeToTaskClickedId()
    }

    private fun subscribeToTaskClickedId() {
        viewModel.selectSubscribe(TaskListState::taskClickedId) { taskClickedId ->
            if (taskClickedId > 0) {
                findNavController().navigateTo(
                    R.id.detailFragment,
                    TaskCommonInput(taskClickedId)
                )
                viewModel.onNavigatedToTaskDetail()
            }
        }
    }

    @AssistedInject.Factory
    interface Factory {

        fun create(viewModel: TaskListViewModel): TaskListViewBinding
    }
}
