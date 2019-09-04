package com.wayfair.todomvrxdatabinding.tasklist

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized

data class TaskListState(
    val tasks: Async<List<TaskListItem>> = Uninitialized,
    val taskClickedId: Int = 0
) : MvRxState
