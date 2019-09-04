package com.wayfair.todomvrxdatabinding.tasklist

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wayfair.todomvrxdatabinding.BindableListAdapter

internal class TaskAdapter(
    private val viewModel: TaskListViewModel
) : BindableListAdapter<TaskListItem, TaskAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent, TaskAdapterItemViewBinding(), viewModel)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemId(position: Int) = getItem(position).id.toLong()

    class ViewHolder(
        parent: ViewGroup,
        private val viewBinding: TaskAdapterItemViewBinding,
        private val viewModel: TaskListViewModel
    ) : RecyclerView.ViewHolder(
        viewBinding.createView(parent)
    ) {

        fun bind(taskListItem: TaskListItem) {
            viewBinding.bindItem(taskListItem, viewModel)
        }
    }
}
