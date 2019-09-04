package com.wayfair.todomvrxdatabinding.taskstatistics

import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import com.wayfair.todomvrxdatabinding.BaseViewModel
import com.wayfair.todomvrxdatabinding.ViewModelFactory
import com.wayfair.todomvrxdatabinding.ktx.createViewModel
import io.reactivex.schedulers.Schedulers

internal class TaskStatisticsViewModel @AssistedInject constructor(
    private val statisticsRepository: TaskStatisticsRepository,
    @Assisted initialState: TaskStatisticsState
) : BaseViewModel<TaskStatisticsState>(initialState) {

    init {
        observeStatsVisibility()
        observeCompleteTasksCount()
        observeIncompleteTasksCount()
    }

    private fun observeStatsVisibility() {
        statisticsRepository.observeAllTasksCount()
            .subscribeOn(Schedulers.io())
            .execute {
                copy(shouldShowStats = it().isBiggerThanZero())
            }
    }

    private fun observeCompleteTasksCount() {
        statisticsRepository.observeCompleteTasksCount()
            .subscribeOn(Schedulers.io())
            .execute {
                copy(numberCompleteTasks = it)
            }
    }

    private fun observeIncompleteTasksCount() {
        statisticsRepository.observeIncompleteTasksCount()
            .subscribeOn(Schedulers.io())
            .execute {
                copy(numberIncompleteTasks = it)
            }
    }

    private fun Int?.isBiggerThanZero(): Boolean = this?.let { it > 0 } ?: false

    @AssistedInject.Factory
    interface Factory : ViewModelFactory<TaskStatisticsState> {
        override fun create(initialState: TaskStatisticsState): TaskStatisticsViewModel
    }

    companion object : MvRxViewModelFactory<TaskStatisticsViewModel, TaskStatisticsState> {
        override fun create(viewModelContext: ViewModelContext, state: TaskStatisticsState): TaskStatisticsViewModel =
            viewModelContext.createViewModel(state)
    }
}
