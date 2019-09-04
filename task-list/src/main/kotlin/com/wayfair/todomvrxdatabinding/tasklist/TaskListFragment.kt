package com.wayfair.todomvrxdatabinding.tasklist

import android.os.Bundle
import android.view.View
import com.airbnb.mvrx.fragmentViewModel
import com.wayfair.todomvrxdatabinding.BaseFragment
import com.wayfair.todomvrxdatabinding.HasViewModelFactory
import com.wayfair.todomvrxdatabinding.ViewModelFactory
import com.wayfair.todomvrxdatabinding.tasklist.databinding.TaskListFragmentBinding
import javax.inject.Inject

class TaskListFragment : BaseFragment<TaskListFragmentBinding>(), HasViewModelFactory<TaskListState> {

    @Inject
    internal lateinit var viewModelFactory: TaskListViewModel.Factory
    @Inject
    internal lateinit var viewBindingFactory: TaskListViewBinding.Factory

    override val factory: ViewModelFactory<TaskListState>
        get() = viewModelFactory

    override val layoutId: Int
        get() = R.layout.task_list_fragment

    private val viewModel by fragmentViewModel(TaskListViewModel::class)

    override fun invalidate() = viewDataBinding.viewBinding!!.notifyChange()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.viewBinding = viewBindingFactory.create(viewModel)
    }
}
