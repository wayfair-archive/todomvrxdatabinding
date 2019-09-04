package com.wayfair.todomvrxdatabinding

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BindableListAdapter<D : BindableItem, VH : RecyclerView.ViewHolder>(
    callback: DiffUtil.ItemCallback<D> = DiffUtilCallback()
) : ListAdapter<D, VH>(callback)
