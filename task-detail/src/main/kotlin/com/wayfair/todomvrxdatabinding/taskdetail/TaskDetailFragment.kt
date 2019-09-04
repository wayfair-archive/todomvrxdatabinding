package com.wayfair.todomvrxdatabinding.taskdetail

import android.os.Bundle
import android.view.View
import com.airbnb.mvrx.fragmentViewModel
import com.wayfair.todomvrxdatabinding.BaseFragment
import com.wayfair.todomvrxdatabinding.HasViewModelFactory
import com.wayfair.todomvrxdatabinding.ViewModelFactory
import com.wayfair.todomvrxdatabinding.taskdetail.databinding.TaskDetailFragmentBinding
import javax.inject.Inject

class TaskDetailFragment : BaseFragment<TaskDetailFragmentBinding>(), HasViewModelFactory<TaskDetailState> {

    @Inject
    internal lateinit var viewModelFactory: TaskDetailViewModel.Factory
    @Inject
    internal lateinit var viewBindingFactory: TaskDetailViewBinding.Factory

    override val factory: ViewModelFactory<TaskDetailState>
        get() = viewModelFactory

    override val layoutId: Int
        get() = R.layout.task_detail_fragment

    private val viewModel by fragmentViewModel(TaskDetailViewModel::class)

    override fun invalidate() = viewDataBinding.viewBinding!!.notifyChange()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.viewBinding = viewBindingFactory.create(viewModel)
    }
}
