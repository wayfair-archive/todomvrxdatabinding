package com.wayfair.todomvrxdatabinding

import androidx.appcompat.widget.Toolbar
import androidx.core.view.size
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@Suppress("UNCHECKED_CAST")
@BindingAdapter("data")
fun RecyclerView.setData(items: List<BindableItem>?) {
    items?.let {
        (adapter as BindableListAdapter<BindableItem, RecyclerView.ViewHolder>).submitList(it)
    }
}

@BindingAdapter("inflateMenu")
fun Toolbar.setInflateMenu(toolbarMenuResId: Int) {
    if (menu.size == 0) inflateMenu(toolbarMenuResId)
}
