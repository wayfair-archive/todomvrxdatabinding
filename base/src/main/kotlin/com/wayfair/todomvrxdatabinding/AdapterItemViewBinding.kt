package com.wayfair.todomvrxdatabinding

import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PROTECTED
import androidx.databinding.ViewDataBinding
import com.airbnb.mvrx.BaseMvRxViewModel
import com.wayfair.todomvrxdatabinding.ktx.inflateDataBinding

abstract class AdapterItemViewBinding<I : BindableItem, VDB : ViewDataBinding, VM : BaseMvRxViewModel<*>> {

    protected val resources: Resources by lazy {
        viewDataBinding.root.context.resources
    }

    @VisibleForTesting(otherwise = PROTECTED)
    lateinit var viewDataBinding: VDB

    protected lateinit var item: I
        private set
    protected lateinit var viewModel: VM
        private set

    abstract fun createView(parent: ViewGroup): View

    open fun bindItem(item: I, viewModel: VM) {
        this.item = item
        this.viewModel = viewModel

        viewDataBinding.setVariable(BR.viewBinding, this)
        viewDataBinding.executePendingBindings()
    }

    fun ViewGroup.inflate(layoutId: Int): View {
        viewDataBinding = inflateDataBinding(layoutId)
        return viewDataBinding.root
    }
}
