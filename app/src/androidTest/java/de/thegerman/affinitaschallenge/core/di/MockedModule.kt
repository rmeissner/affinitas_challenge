package de.thegerman.affinitaschallenge.core.di

import dagger.Module
import dagger.Provides
import de.thegerman.affinitaschallenge.core.managers.InAppNotificationManager
import de.thegerman.affinitaschallenge.networking.ImageLoader
import de.thegerman.affinitaschallenge.repositories.ReceiptsRepository
import de.thegerman.affinitaschallenge.repositories.UserRepository
import org.mockito.Mockito
import javax.inject.Singleton

@Module
class MockedModule {

    @Provides
    @Singleton
    fun providesImageLoader(): ImageLoader {
        return Mockito.mock(ImageLoader::class.java)
    }

    @Provides
    @Singleton
    fun providesUserRepository(): UserRepository {
        return Mockito.mock(UserRepository::class.java)
    }

    @Provides
    @Singleton
    fun providesReceiptsRepository(): ReceiptsRepository {
        return Mockito.mock(ReceiptsRepository::class.java)
    }

    @Provides
    @Singleton
    fun providesInAppNotificationManager(): InAppNotificationManager {
        return Mockito.mock(InAppNotificationManager::class.java)
    }
}