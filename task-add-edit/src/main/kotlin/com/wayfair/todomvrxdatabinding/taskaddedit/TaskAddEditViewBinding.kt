package com.wayfair.todomvrxdatabinding.taskaddedit

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.view.View.OnClickListener
import com.airbnb.mvrx.withState
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import com.wayfair.todomvrxdatabinding.BaseViewBinding
import com.wayfair.todomvrxdatabinding.NavigationViewBinding
import com.wayfair.todomvrxdatabinding.ktx.hideKeyboard
import com.wayfair.todomvrxdatabinding.ktx.showSnackbar

internal class TaskAddEditViewBinding @AssistedInject constructor(
    fragment: TaskAddEditFragment,
    @Assisted override val viewModel: TaskAddEditViewModel,
    val navigationViewBinding: NavigationViewBinding,
    resources: Resources
) : BaseViewBinding<TaskAddEditState>(fragment, viewModel) {

    val onFabClickListener = OnClickListener {
        withState(viewModel) {
            viewModel.onSaveTask()
        }
    }

    val fabImage: Drawable = resources.getDrawable(R.drawable.task_add_edit_ic_done)!!

    var taskTitle: String
        set(value) = viewModel.onUpdateTaskTitle(value)
        get() = withState(viewModel) {
            it.task()!!.title
        }

    var taskDescription: String
        set(value) = viewModel.onUpdateTaskDescription(value)
        get() = withState(viewModel) {
            it.task()!!.description
        }

    init {
        navigationViewBinding.toolbarTitle = resources.getString(R.string.task_add_edit_title)
        subscribeToIsTaskSuccessfullySaved()
        subscribeToValidationFailed()
    }

    private fun subscribeToValidationFailed() {
        viewModel.selectSubscribe(TaskAddEditState::validationFailed) { validationFailed ->
            if (validationFailed) {
                view.hideKeyboard()
                view.showSnackbar(R.string.task_add_edit_empty_field)
                viewModel.onValidationToastShowed()
            }
        }
    }

    private fun subscribeToIsTaskSuccessfullySaved() {
        viewModel.selectSubscribe(TaskAddEditState::isTaskSuccessfullySaved) { isTaskSaved ->
            if (isTaskSaved) {
                view.hideKeyboard()
                findNavController().navigateUp()
            }
        }
    }

    @AssistedInject.Factory
    interface Factory {

        fun create(viewModel: TaskAddEditViewModel): TaskAddEditViewBinding
    }
}
