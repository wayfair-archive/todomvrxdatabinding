package com.wayfair.todomvrxdatabinding.taskstatistics

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized

data class TaskStatisticsState(
    val shouldShowStats: Boolean = false,
    val numberCompleteTasks: Async<Int> = Uninitialized,
    val numberIncompleteTasks: Async<Int> = Uninitialized
) : MvRxState
