package com.tanikoszyk.common.extensions

import me.tatarka.bindingcollectionadapter2.collections.DiffObservableList

fun <T> createDiffObservableList(
    areItemsTheSame: (oldItem: T, newItem: T) -> Boolean,
    areContentsTheSame: (oldItem: T, newItem: T) -> Boolean
): DiffObservableList<T> {
    return DiffObservableList<T>(
        object : DiffObservableList.Callback<T> {
            override fun areItemsTheSame(oldItem: T, newItem: T) = areItemsTheSame(oldItem, newItem)
            override fun areContentsTheSame(oldItem: T, newItem: T) = areContentsTheSame(oldItem, newItem)
        })
}