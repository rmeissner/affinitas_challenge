package de.thegerman.affinitaschallenge.repositories.entries

import de.thegerman.affinitaschallenge.R
import de.thegerman.affinitaschallenge.core.adapter.BaseAdapter
import de.thegerman.affinitaschallenge.repositories.models.Receipt


class ReceiptEntry(val receipt: Receipt): BaseAdapter.Entry(receipt.id, R.id.entry_type_receipt)