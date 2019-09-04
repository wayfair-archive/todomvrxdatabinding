package com.wayfair.todomvrxdatabinding.taskstatistics

import android.content.res.Resources
import android.view.View.GONE
import android.view.View.VISIBLE
import com.airbnb.mvrx.withState
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import com.wayfair.todomvrxdatabinding.BaseViewBinding
import com.wayfair.todomvrxdatabinding.NavigationViewBinding

internal class TaskStatisticsViewBinding @AssistedInject constructor(
    fragment: TaskStatisticsFragment,
    @Assisted override val viewModel: TaskStatisticsViewModel,
    val navigationViewBinding: NavigationViewBinding,
    private val resources: Resources
) : BaseViewBinding<TaskStatisticsState>(fragment, viewModel) {

    val emptyStatsPlaceholderVisibility: Int
        get() = withState(viewModel) {
            if (it.shouldShowStats) GONE else VISIBLE
        }

    val statsVisibility: Int
        get() = withState(viewModel) {
            if (it.shouldShowStats) VISIBLE else GONE
        }

    val incompleteTasksText: String
        get() = withState(viewModel) {
            resources.getString(R.string.task_statistics_incomplete_tasks, it.numberIncompleteTasks())
        }

    val completeTasksText: String
        get() = withState(viewModel) {
            resources.getString(R.string.task_statistics_complete_tasks, it.numberCompleteTasks())
        }

    init {
        navigationViewBinding.toolbarTitle = resources.getString(R.string.task_statistics_title)
    }

    @AssistedInject.Factory
    interface Factory {

        fun create(viewModel: TaskStatisticsViewModel): TaskStatisticsViewBinding
    }
}
