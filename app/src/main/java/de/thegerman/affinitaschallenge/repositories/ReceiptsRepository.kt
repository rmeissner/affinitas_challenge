package de.thegerman.affinitaschallenge.repositories

import de.thegerman.affinitaschallenge.repositories.entries.ReceiptEntry
import io.reactivex.Flowable
import io.reactivex.Observable


interface ReceiptsRepository {
    fun observeFavorite(receiptId: String): Flowable<Boolean>

    fun observeRating(receiptId: String): Flowable<Int>

    fun rate(receiptId: String, rating: Int)

    fun favorite(receiptId: String, favorite: Boolean)

    fun loadReceipts(): Observable<List<ReceiptEntry>>
}