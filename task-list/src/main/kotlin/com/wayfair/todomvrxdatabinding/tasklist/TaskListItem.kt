package com.wayfair.todomvrxdatabinding.tasklist

import com.wayfair.todomvrxdatabinding.BindableItem

data class TaskListItem(
    override val id: Int,
    val title: String,
    val complete: Boolean
) : BindableItem
