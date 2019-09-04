package com.wayfair.todomvrxdatabinding.ktx

import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.FragmentViewModelContext
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.ViewModelContext
import com.wayfair.todomvrxdatabinding.HasViewModelFactory

@Suppress("UNCHECKED_CAST")
inline fun <reified VM : BaseMvRxViewModel<S>, reified S : MvRxState> ViewModelContext.createViewModel(initialState: S) =
    ((this as FragmentViewModelContext).fragment as HasViewModelFactory<S>).factory.create(initialState) as VM
