package com.wayfair.todomvrxdatabinding.taskaddedit

import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.ViewModelContext
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import com.wayfair.todomvrxdatabinding.BaseViewModel
import com.wayfair.todomvrxdatabinding.ViewModelFactory
import com.wayfair.todomvrxdatabinding.data.Task
import com.wayfair.todomvrxdatabinding.ktx.createViewModel
import com.wayfair.todomvrxdatabinding.taskcommon.TaskRepository
import io.reactivex.schedulers.Schedulers

internal class TaskAddEditViewModel @AssistedInject constructor(
    private val repository: TaskRepository,
    @Assisted initialState: TaskAddEditState
) : BaseViewModel<TaskAddEditState>(initialState) {

    init {
        if (initialState.input != null) {
            observeTaskChanges(initialState.input.taskId)
        } else {
            setState {
                copy(task = Success(Task()))
            }
        }
    }

    private fun observeTaskChanges(taskId: Int) {
        repository.observeTask(taskId)
            .subscribeOn(Schedulers.io())
            .execute {
                copy(task = it)
            }
    }

    fun onSaveTask() = withState {
        if (it.task()!!.title.isBlank()) {
            setState { copy(validationFailed = true) }
            return@withState
        }
        repository.saveTask(it.task()!!)
            .subscribeOn(Schedulers.io())
            .execute {
                copy(isTaskSuccessfullySaved = true)
            }
    }

    fun onUpdateTaskTitle(title: String) = setState {
        copy(task = Success(task()!!.copy(title = title)))
    }

    fun onUpdateTaskDescription(description: String) = setState {
        copy(task = Success(task()!!.copy(description = description)))
    }

    fun onValidationToastShowed() = setState {
        copy(validationFailed = false)
    }

    @AssistedInject.Factory
    interface Factory : ViewModelFactory<TaskAddEditState> {
        override fun create(initialState: TaskAddEditState): TaskAddEditViewModel
    }

    companion object : MvRxViewModelFactory<TaskAddEditViewModel, TaskAddEditState> {
        override fun create(viewModelContext: ViewModelContext, state: TaskAddEditState): TaskAddEditViewModel =
            viewModelContext.createViewModel(state)

        override fun initialState(viewModelContext: ViewModelContext): TaskAddEditState =
            TaskAddEditState(input = viewModelContext.args())
    }
}
