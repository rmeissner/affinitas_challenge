package de.thegerman.affinitaschallenge.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import de.thegerman.affinitaschallenge.R
import de.thegerman.affinitaschallenge.core.adapter.BaseAdapter
import de.thegerman.affinitaschallenge.core.adapter.BaseViewHolder
import de.thegerman.affinitaschallenge.core.adapter.FallbackViewHolder
import de.thegerman.affinitaschallenge.networking.ImageLoader
import de.thegerman.affinitaschallenge.repositories.ReceiptsRepository
import de.thegerman.affinitaschallenge.repositories.entries.ReceiptEntry
import de.thegerman.onekay.core.di.ForView
import kotlinx.android.synthetic.main.list_entry_receipt.view.*
import javax.inject.Inject

@ForView
class ReceiptsAdapter @Inject constructor(
        val context: Context, val imageLoader: ImageLoader, val repository: ReceiptsRepository
): BaseAdapter<ReceiptEntry>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder<ReceiptEntry> {
        when(viewType) {
            R.id.entry_type_receipt -> {
                val inflater = LayoutInflater.from(context)
                val layout = inflater.inflate(R.layout.list_entry_receipt, parent, false)
                val binding =  ReceiptViewHolder.Binding(layout, layout.title_text, layout.image,
                        layout.favorite_button, layout.rate_1_button, layout.rate_2_button,
                        layout.rate_3_button, layout.rate_4_button, layout.rate_5_button)
                return ReceiptViewHolder(binding, imageLoader, repository)
            }
            else -> {
                return FallbackViewHolder(context)
            }
        }
    }
}