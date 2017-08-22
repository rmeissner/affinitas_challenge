package de.thegerman.affinitaschallenge.core.adapter

import android.content.Context
import android.view.View

/**
 * This view holder can be returned if we encounter an unknown type in an adapter.
 */
class FallbackViewHolder(context: Context) : BaseViewHolder<BaseAdapter.Entry>(View(context), BaseAdapter.Entry::class.java) {
    override fun bindCasted(entry: BaseAdapter.Entry, payloads: List<Any>?) {
    }
}