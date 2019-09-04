package com.wayfair.todomvrxdatabinding.ktx

import android.os.Parcelable
import androidx.annotation.IdRes
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.airbnb.mvrx.MvRx

fun NavController.navigateTo(@IdRes id: Int, args: Parcelable? = null) {
    navigate(id, bundleOf(MvRx.KEY_ARG to args))
}