package com.wayfair.todomvrxdatabinding

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.airbnb.mvrx.BaseMvRxFragment
import com.wayfair.todomvrxdatabinding.ktx.inflateDataBinding
import dagger.android.support.AndroidSupportInjection

abstract class BaseFragment<VDB : ViewDataBinding> : BaseMvRxFragment() {

    protected lateinit var viewDataBinding: VDB

    @get:LayoutRes
    abstract val layoutId: Int

    @CallSuper
    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    @CallSuper
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewDataBinding = container!!.inflateDataBinding(layoutId)
        return viewDataBinding.root
    }
}
