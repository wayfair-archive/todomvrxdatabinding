package com.wayfair.todomvrxdatabinding

import android.annotation.SuppressLint
import androidx.databinding.BaseObservable
import androidx.navigation.fragment.findNavController
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.MvRxView

@SuppressLint("RxJava2MethodMissingCheckReturnValue") // False positive, https://github.com/vanniktech/lint-rules/issues/286
abstract class BaseViewBinding<S : MvRxState>(
    open val fragment: BaseMvRxFragment,
    open val viewModel: BaseMvRxViewModel<S>
) : BaseObservable(), MvRxView by fragment {

    protected fun findNavController() = fragment.findNavController()

    protected val view get() = fragment.requireView()

    protected val context get() = fragment.context!!
}
