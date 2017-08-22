package de.thegerman.onekay.storage

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import de.thegerman.affinitaschallenge.storage.ReceiptsCacheDao
import de.thegerman.affinitaschallenge.storage.models.CachedFavorite
import de.thegerman.affinitaschallenge.storage.models.CachedRating

@Database(entities = arrayOf(CachedRating::class, CachedFavorite::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun receiptsDao(): ReceiptsCacheDao
}