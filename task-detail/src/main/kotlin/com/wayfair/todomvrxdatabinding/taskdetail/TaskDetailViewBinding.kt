package com.wayfair.todomvrxdatabinding.taskdetail

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.view.View.OnClickListener
import androidx.appcompat.widget.Toolbar
import com.airbnb.mvrx.withState
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import com.wayfair.todomvrxdatabinding.BaseViewBinding
import com.wayfair.todomvrxdatabinding.NavigationViewBinding
import com.wayfair.todomvrxdatabinding.data.TaskCommonInput
import com.wayfair.todomvrxdatabinding.ktx.navigateTo

internal class TaskDetailViewBinding @AssistedInject constructor(
    fragment: TaskDetailFragment,
    @Assisted override val viewModel: TaskDetailViewModel,
    val navigationViewBinding: NavigationViewBinding,
    resources: Resources
) : BaseViewBinding<TaskDetailState>(fragment, viewModel) {

    val onFabClickListener = OnClickListener {
        withState(viewModel) {
            findNavController().navigateTo(
                R.id.addEditFragment,
                TaskCommonInput(it.input.taskId)
            )
        }
    }

    val fabImage: Drawable = resources.getDrawable(R.drawable.task_detail_ic_edit)!!

    val taskTitle: String?
        get() = withState(viewModel) {
            it.task()?.title
        }

    val taskDescription: String?
        get() = withState(viewModel) {
            it.task()?.description
        }

    val isTaskComplete: Boolean
        get() = withState(viewModel) {
            it.task()?.complete ?: false
        }

    val taskCheckboxClickListener = OnClickListener {
        viewModel.onTaskStatusToggleClick()
    }

    init {
        navigationViewBinding.toolbarTitle = resources.getString(R.string.task_detail_title)
        navigationViewBinding.toolbarMenuResId = R.menu.task_detail_menu
        navigationViewBinding.menuItemClickListener = Toolbar.OnMenuItemClickListener {
            viewModel.onMenuDeleteTaskClick()
            true
        }
        subscribeToTaskDeleted()
    }

    private fun subscribeToTaskDeleted() {
        viewModel.selectSubscribe(TaskDetailState::taskDeleted) { taskDeleted ->
            if (taskDeleted) {
                findNavController().navigateUp()
            }
        }
    }

    @AssistedInject.Factory
    interface Factory {

        fun create(viewModel: TaskDetailViewModel): TaskDetailViewBinding
    }
}
