package de.thegerman.affinitaschallenge.core.di

import dagger.Binds
import dagger.Module
import de.thegerman.affinitaschallenge.core.managers.InAppNotificationManager
import de.thegerman.affinitaschallenge.core.managers.impls.SnackbarInAppNotificationManager
import javax.inject.Singleton

@Module
abstract class CoreModule {
    @Binds
    @Singleton
    abstract fun providesInAppNotificationManager(manager: SnackbarInAppNotificationManager): InAppNotificationManager
}