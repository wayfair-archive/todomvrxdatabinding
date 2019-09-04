package com.wayfair.todomvrxdatabinding.taskdetail

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import com.wayfair.todomvrxdatabinding.data.Task
import com.wayfair.todomvrxdatabinding.data.TaskCommonInput

data class TaskDetailState(
    val input: TaskCommonInput,
    val task: Async<Task> = Uninitialized,
    val taskDeleted: Boolean = false
) : MvRxState
