package com.wayfair.todomvrxdatabinding.taskstatistics

import android.os.Bundle
import android.view.View
import com.airbnb.mvrx.fragmentViewModel
import com.wayfair.todomvrxdatabinding.BaseFragment
import com.wayfair.todomvrxdatabinding.HasViewModelFactory
import com.wayfair.todomvrxdatabinding.ViewModelFactory
import com.wayfair.todomvrxdatabinding.taskstatistics.databinding.TaskStatisticsFragmentBinding
import javax.inject.Inject

class TaskStatisticsFragment : BaseFragment<TaskStatisticsFragmentBinding>(), HasViewModelFactory<TaskStatisticsState> {

    @Inject
    internal lateinit var viewModelFactory: TaskStatisticsViewModel.Factory
    @Inject
    internal lateinit var viewBindingFactory: TaskStatisticsViewBinding.Factory

    override val factory: ViewModelFactory<TaskStatisticsState>
        get() = viewModelFactory

    override val layoutId: Int
        get() = R.layout.task_statistics_fragment

    private val viewModel by fragmentViewModel(TaskStatisticsViewModel::class)

    override fun invalidate() = viewDataBinding.viewBinding!!.notifyChange()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.viewBinding = viewBindingFactory.create(viewModel)
    }
}
