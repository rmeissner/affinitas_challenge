package de.thegerman.affinitaschallenge.storage

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import de.thegerman.affinitaschallenge.storage.models.CachedFavorite
import de.thegerman.affinitaschallenge.storage.models.CachedRating
import io.reactivex.Flowable


@Dao
interface ReceiptsCacheDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favorite: CachedFavorite): Long

    @Query("SELECT * FROM " + CachedFavorite.TABLE_NAME + " WHERE receiptsId = :arg0 LIMIT 1")
    fun observeFavorite(receiptsId: String): Flowable<CachedFavorite>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favorite: CachedRating): Long

    @Query("SELECT * FROM " + CachedRating.TABLE_NAME + " WHERE receiptsId = :arg0 LIMIT 1")
    fun observeRating(receiptsId: String): Flowable<CachedRating>
}