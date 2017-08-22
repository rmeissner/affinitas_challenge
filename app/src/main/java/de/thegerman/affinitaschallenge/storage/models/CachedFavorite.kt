package de.thegerman.affinitaschallenge.storage.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = CachedFavorite.TABLE_NAME)
data class CachedFavorite(
        @PrimaryKey
        var receiptsId: String = "",
        var favorite: Boolean = false
) {
    companion object {
        const val TABLE_NAME = "favorties"
    }
}