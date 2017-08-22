package de.thegerman.affinitaschallenge.core.utils

import android.support.v7.widget.RecyclerView

object RecyclerViewUtils {

    fun unbindAllViewHolders(recyclerView: RecyclerView) {
        // We want to unbind all attached views (to prevent memory leaks), when the view holder is destroyed.
        var i = 0
        val size = recyclerView.childCount
        while (i < size) {
            val childAt = recyclerView.getChildAt(i)
            if (childAt == null) {
                i++
                continue
            }
            val viewHolder = recyclerView.getChildViewHolder(childAt)
            //(viewHolder as? BaseViewHolder<*>)?.unbind()
            i++
        }
    }
}
