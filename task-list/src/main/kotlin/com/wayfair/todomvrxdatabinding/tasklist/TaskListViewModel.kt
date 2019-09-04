package com.wayfair.todomvrxdatabinding.tasklist

import android.annotation.SuppressLint
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import com.wayfair.todomvrxdatabinding.BaseViewModel
import com.wayfair.todomvrxdatabinding.ViewModelFactory
import com.wayfair.todomvrxdatabinding.data.Task
import com.wayfair.todomvrxdatabinding.ktx.createViewModel
import io.reactivex.schedulers.Schedulers

internal class TaskListViewModel @AssistedInject constructor(
    private val repository: TaskListRepository,
    @Assisted initialState: TaskListState
) : BaseViewModel<TaskListState>(initialState) {

    init {
        observeTasksChanges()
    }

    private fun observeTasksChanges() {
        withState { state ->
            repository.observeTasks(delay = state.tasks.shouldLoad)
                .subscribeOn(Schedulers.io())
                .map { it.mapToTaskItem() }
                .execute {
                    copy(tasks = it)
                }
        }
    }

    fun onTaskClick(taskListItem: TaskListItem) = setState {
        copy(taskClickedId = taskListItem.id)
    }

    @SuppressLint("RxJava2SubscribeMissingOnError")
    fun onTaskCheckboxClick(taskListItem: TaskListItem) {
        repository.setComplete(taskListItem.id, !taskListItem.complete)
            .subscribeOn(Schedulers.io())
            .subscribe() // used instead of `execute` since we don't handle the result
            .disposeOnClear()
    }

    fun onNavigatedToTaskDetail() = setState {
        copy(taskClickedId = 0)
    }

    @SuppressLint("RxJava2SubscribeMissingOnError")
    fun onMenuClearAllClick() {
        repository.clearTasks()
            .subscribeOn(Schedulers.io())
            .subscribe() // used instead of `execute` since we don't handle the result
            .disposeOnClear()
    }

    @AssistedInject.Factory
    interface Factory : ViewModelFactory<TaskListState> {
        override fun create(initialState: TaskListState): TaskListViewModel
    }

    companion object : MvRxViewModelFactory<TaskListViewModel, TaskListState> {
        override fun create(viewModelContext: ViewModelContext, state: TaskListState): TaskListViewModel =
            viewModelContext.createViewModel(state)

        private fun List<Task>.mapToTaskItem() = map { task ->
            TaskListItem(task.id, task.title, task.complete)
        }
    }
}
