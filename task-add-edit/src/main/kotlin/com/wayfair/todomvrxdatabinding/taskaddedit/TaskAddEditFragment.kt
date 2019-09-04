package com.wayfair.todomvrxdatabinding.taskaddedit

import android.os.Bundle
import android.view.View
import com.airbnb.mvrx.fragmentViewModel
import com.wayfair.todomvrxdatabinding.BaseFragment
import com.wayfair.todomvrxdatabinding.HasViewModelFactory
import com.wayfair.todomvrxdatabinding.ViewModelFactory
import com.wayfair.todomvrxdatabinding.taskaddedit.databinding.TaskAddEditFragmentBinding
import javax.inject.Inject

class TaskAddEditFragment : BaseFragment<TaskAddEditFragmentBinding>(), HasViewModelFactory<TaskAddEditState> {

    @Inject
    internal lateinit var viewModelFactory: TaskAddEditViewModel.Factory
    @Inject
    internal lateinit var viewBindingFactory: TaskAddEditViewBinding.Factory

    override val factory: ViewModelFactory<TaskAddEditState>
        get() = viewModelFactory

    override val layoutId: Int
        get() = R.layout.task_add_edit_fragment

    private val viewModel by fragmentViewModel(TaskAddEditViewModel::class)

    override fun invalidate() = viewDataBinding.viewBinding!!.notifyChange()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding.viewBinding = viewBindingFactory.create(viewModel)
    }
}
