package com.example.progo.ui.component.dragDropList

import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListState

fun LazyListState.getVisibleItemInfoFor(absolute: Int): LazyListItemInfo? {
    return this.layoutInfo.visibleItemsInfo.getOrNull(absolute - this.layoutInfo.visibleItemsInfo.first().index)
}

val LazyListItemInfo.offsetEnd: Int
    get() = this.offset + this.size

fun <T> MutableList<T>.move(from: Int, to: Int) {
    println(from)
    println(to)
    if (from == to)
        return

    val element = this.removeAt(from) ?: return
    this.add(to, element)
}