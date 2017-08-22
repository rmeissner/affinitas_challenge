package de.thegerman.affinitaschallenge.core.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View


abstract class BaseViewHolder<in T : BaseAdapter.Entry>(itemView: View, private val entryClass: Class<T>) :
        RecyclerView.ViewHolder(itemView) {

    fun bind(entry: Any?, payloads: List<Any>?) {
        if (entryClass.isInstance(entry)) {
            bindCasted(entryClass.cast(entry), payloads)
        }
    }

    protected val context: Context
        get() = itemView.context

    open fun detached() {}

    open fun unbind() {}

    abstract fun bindCasted(entry: T, payloads: List<Any>?)
}