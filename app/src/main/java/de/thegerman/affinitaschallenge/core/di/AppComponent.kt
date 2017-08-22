package de.thegerman.onekay.core.di

import android.content.Context
import de.thegerman.affinitaschallenge.core.ChallengeApplication
import de.thegerman.affinitaschallenge.core.managers.InAppNotificationManager
import de.thegerman.affinitaschallenge.networking.ImageLoader
import de.thegerman.affinitaschallenge.repositories.ReceiptsRepository
import de.thegerman.affinitaschallenge.repositories.UserRepository


interface AppComponent {
    fun application(): ChallengeApplication

    fun context(): Context

    fun imageLoader(): ImageLoader

    fun inAppNotificationManager(): InAppNotificationManager

    // Repositories

    fun receiptsRepository(): ReceiptsRepository

    fun userRepository(): UserRepository

    // Injects for base classes
    fun inject(application: ChallengeApplication)
}