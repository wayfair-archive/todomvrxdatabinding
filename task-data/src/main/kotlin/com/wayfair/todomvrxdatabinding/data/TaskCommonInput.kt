package com.wayfair.todomvrxdatabinding.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TaskCommonInput(
    val taskId: Int
) : Parcelable
