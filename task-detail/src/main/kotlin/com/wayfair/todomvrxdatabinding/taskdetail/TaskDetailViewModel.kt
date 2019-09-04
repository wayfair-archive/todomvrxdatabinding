package com.wayfair.todomvrxdatabinding.taskdetail

import android.annotation.SuppressLint
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import com.wayfair.todomvrxdatabinding.BaseViewModel
import com.wayfair.todomvrxdatabinding.ViewModelFactory
import com.wayfair.todomvrxdatabinding.ktx.createViewModel
import com.wayfair.todomvrxdatabinding.taskcommon.TaskRepository
import io.reactivex.schedulers.Schedulers

internal class TaskDetailViewModel @AssistedInject constructor(
    private val repository: TaskRepository,
    @Assisted initialState: TaskDetailState
) : BaseViewModel<TaskDetailState>(initialState) {

    init {
        observeTaskChanges()
    }

    private fun observeTaskChanges() = withState { state ->
        repository.observeTask(state.input.taskId)
            .subscribeOn(Schedulers.io())
            .execute {
                copy(task = it)
            }
    }

    @SuppressLint("RxJava2SubscribeMissingOnError")
    fun onTaskStatusToggleClick() = withState {
        repository.saveTask(it.task()!!.copy(complete = !it.task()!!.complete))
            .subscribeOn(Schedulers.io())
            .subscribe()
            .disposeOnClear()
    }

    fun onMenuDeleteTaskClick() = withState {
        repository.deleteTask(it.task()!!.id)
            .subscribeOn(Schedulers.io())
            .execute {
                copy(taskDeleted = true)
            }
    }

    @AssistedInject.Factory
    interface Factory : ViewModelFactory<TaskDetailState> {
        override fun create(initialState: TaskDetailState): TaskDetailViewModel
    }

    companion object : MvRxViewModelFactory<TaskDetailViewModel, TaskDetailState> {
        override fun create(viewModelContext: ViewModelContext, state: TaskDetailState): TaskDetailViewModel =
            viewModelContext.createViewModel(state)

        override fun initialState(viewModelContext: ViewModelContext): TaskDetailState? =
            TaskDetailState(input = viewModelContext.args())
    }
}
