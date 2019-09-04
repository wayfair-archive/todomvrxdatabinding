package com.wayfair.todomvrxdatabinding

import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxState

interface ViewModelFactory<S : MvRxState> {

    fun create(initialState: S): BaseMvRxViewModel<S>
}

interface HasViewModelFactory<S : MvRxState> {

    val factory: ViewModelFactory<S>
}
