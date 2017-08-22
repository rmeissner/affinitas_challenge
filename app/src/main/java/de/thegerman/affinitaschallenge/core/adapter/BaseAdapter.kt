package de.thegerman.affinitaschallenge.core.adapter

import android.support.v7.widget.RecyclerView
import de.thegerman.affinitaschallenge.core.utils.StringUtils


abstract class BaseAdapter<T : BaseAdapter.Entry> : RecyclerView.Adapter<BaseViewHolder<T>>() {

    private var data: AdapterData<T>? = null

    override fun onViewDetachedFromWindow(holder: BaseViewHolder<T>?) {
        holder?.detached()
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int, payloads: List<Any>?) {
        holder.bind(data?.entries?.getOrNull(position), payloads)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        onBindViewHolder(holder, position, null)
    }

    override fun onViewRecycled(holder: BaseViewHolder<T>?) {
        holder?.unbind()
    }

    override fun getItemCount(): Int {
        return data?.entries?.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        return data?.entries?.getOrNull(position)?.type ?: 0
    }

    fun update(update: AdapterUpdate<T>) {
        val previousDataUuid = data?.uuid
        data = update.data
        // Check if the update was based on the previous data set, if so use the diff results to
        // update the adapter, else invalidate everything
        if (update.diff != null && StringUtils.equals(update.parentUuid, previousDataUuid)) {
            update.diff.dispatchUpdatesTo(this)
        } else {
            notifyDataSetChanged()
        }
    }

    abstract class Entry protected constructor(val key: String, val type: Int) {

        open fun isSame(other: Entry?): Boolean {
            return other != null && StringUtils.equals(key, other.key)
        }

        /**
         * Will calculate a payload for the changes

         * @param oldEntry to which the difference will be calculated
         * *
         * @return A change object or null if the content is the same
         */
        open fun getPayload(oldEntry: Entry): Any? {
            return null
        }
    }
}