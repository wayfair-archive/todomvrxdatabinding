package com.wayfair.todomvrxdatabinding.tasklist

import android.view.View.OnClickListener
import android.view.ViewGroup
import com.wayfair.todomvrxdatabinding.AdapterItemViewBinding
import com.wayfair.todomvrxdatabinding.tasklist.databinding.TaskListAdapterItemBinding

internal class TaskAdapterItemViewBinding :
    AdapterItemViewBinding<TaskListItem, TaskListAdapterItemBinding, TaskListViewModel>() {

    override fun createView(parent: ViewGroup) = parent.inflate(R.layout.task_list_adapter_item)

    val title: String
        get() = item.title

    val isComplete: Boolean
        get() = item.complete

    val checkboxClickListener = OnClickListener {
        viewModel.onTaskCheckboxClick(item)
    }

    val taskClickListener = OnClickListener {
        viewModel.onTaskClick(item)
    }
}
