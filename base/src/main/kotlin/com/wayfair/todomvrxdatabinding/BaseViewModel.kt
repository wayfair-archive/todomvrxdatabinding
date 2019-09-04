package com.wayfair.todomvrxdatabinding

import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxState

abstract class BaseViewModel<S : MvRxState>(
    initialState: S
) : BaseMvRxViewModel<S>(initialState, BuildConfig.DEBUG)
