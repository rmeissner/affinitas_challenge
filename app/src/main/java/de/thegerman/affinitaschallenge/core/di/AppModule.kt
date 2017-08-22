package de.thegerman.onekay.core.di

import android.content.Context
import dagger.Module
import dagger.Provides
import de.thegerman.affinitaschallenge.core.ChallengeApplication
import javax.inject.Singleton

@Module
class AppModule(private val application: ChallengeApplication) {
    @Provides
    @Singleton
    fun providesApplication(): ChallengeApplication {
        return application
    }

    @Provides
    @Singleton
    fun providesContext(): Context {
        return application.applicationContext
    }
}