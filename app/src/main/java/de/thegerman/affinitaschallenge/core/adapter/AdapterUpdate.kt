package de.thegerman.affinitaschallenge.core.adapter

import android.support.v7.util.DiffUtil


data class AdapterUpdate<T>(val data: AdapterData<T>, val parentUuid: String? = null,
                            val diff: DiffUtil.DiffResult? = null) {


    class DiffCallback(private val oldEntries: AdapterData<BaseAdapter.Entry>,
                       private val newEntries: AdapterData<BaseAdapter.Entry>) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldEntries.entries.size
        }

        override fun getNewListSize(): Int {
            return newEntries.entries.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldEntry = oldEntries.entries[oldItemPosition]
            val newEntry = newEntries.entries[newItemPosition]
            return newEntry.isSame(oldEntry)
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldEntry = oldEntries.entries[oldItemPosition]
            val newEntry = newEntries.entries[newItemPosition]
            return newEntry.getPayload(oldEntry) == null
        }

        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
            val oldEntry = oldEntries.entries[oldItemPosition]
            val newEntry = newEntries.entries[newItemPosition]
            return newEntry.getPayload(oldEntry)
        }
    }
}