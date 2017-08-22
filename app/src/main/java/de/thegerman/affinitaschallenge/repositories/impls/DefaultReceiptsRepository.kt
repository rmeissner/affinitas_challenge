package de.thegerman.affinitaschallenge.repositories.impls

import android.content.Context
import de.thegerman.affinitaschallenge.core.ChallengeLog
import de.thegerman.affinitaschallenge.networking.ReceiptsApi
import de.thegerman.affinitaschallenge.repositories.ReceiptsRepository
import de.thegerman.affinitaschallenge.repositories.entries.ReceiptEntry
import de.thegerman.affinitaschallenge.repositories.models.Receipt
import de.thegerman.affinitaschallenge.storage.ReceiptsCacheDao
import de.thegerman.affinitaschallenge.storage.models.CachedFavorite
import de.thegerman.affinitaschallenge.storage.models.CachedRating
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Callable
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class DefaultReceiptsRepository @Inject constructor(
        val context: Context,
        val receiptsDao: ReceiptsCacheDao,
        val receiptsApi: ReceiptsApi
) : ReceiptsRepository {

    override fun rate(receiptId: String, rating: Int) {
        // Here we should make an api call to store the ratings on the server, for now we only store them locally
        performIo(Callable {
            receiptsDao.insert(CachedRating(receiptId, rating))
        })
    }

    override fun favorite(receiptId: String, favorite: Boolean) {
        // Here we should make an api call to store the favorites on the server, for now we only store them locally
        performIo(Callable {
            receiptsDao.insert(CachedFavorite(receiptId, favorite))
        })
    }

    private fun <T> performIo(action: Callable<T>) {
        // We use this helper to perform the database query on a non UI thread
        Observable.fromCallable(action)
                .subscribeOn(Schedulers.io())
                .subscribe({}, ChallengeLog::log)
    }

    override fun observeFavorite(receiptId: String): Flowable<Boolean> {
        return receiptsDao.observeFavorite(receiptId).map { it.favorite }
    }

    override fun observeRating(receiptId: String): Flowable<Int> {
        return receiptsDao.observeRating(receiptId).map { it.rating }
    }

    override fun loadReceipts(): Observable<List<ReceiptEntry>> {
        return receiptsApi.load().map {
            it.map {
                ReceiptEntry(Receipt(it.id, it.name, it.description, it.image))
            }
        }
    }
}