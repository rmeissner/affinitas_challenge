package de.thegerman.affinitaschallenge.storage.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = CachedRating.TABLE_NAME)
data class CachedRating(
        @PrimaryKey
        var receiptsId: String = "",
        var rating: Int = 0
) {
    companion object {
        const val TABLE_NAME = "ratings"
    }
}