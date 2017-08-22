package de.thegerman.onekay.storage

import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import android.arch.persistence.room.Room
import android.content.Context
import de.thegerman.affinitaschallenge.storage.ReceiptsCacheDao


@Module
class StorageModule {
    @Provides
    @Singleton
    fun providesAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context,
                AppDatabase::class.java, DATABASE_NAME).build()
    }

    @Provides
    @Singleton
    fun providesReceiptsDao(appDatabase: AppDatabase): ReceiptsCacheDao {
        return appDatabase.receiptsDao()
    }

    companion object {
        private const val DATABASE_NAME = "receipts-database"
    }
}