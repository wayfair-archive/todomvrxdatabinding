package com.wayfair.todomvrxdatabinding.taskaddedit

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import com.wayfair.todomvrxdatabinding.data.Task
import com.wayfair.todomvrxdatabinding.data.TaskCommonInput

data class TaskAddEditState(
    val input: TaskCommonInput? = null,
    val task: Async<Task> = Uninitialized,
    val isTaskSuccessfullySaved: Boolean = false,
    val validationFailed: Boolean = false
) : MvRxState
