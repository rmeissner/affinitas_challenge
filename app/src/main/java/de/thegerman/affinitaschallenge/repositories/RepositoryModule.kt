package de.thegerman.affinitaschallenge.repositories

import dagger.Binds
import dagger.Module
import de.thegerman.affinitaschallenge.repositories.impls.DefaultReceiptsRepository
import de.thegerman.affinitaschallenge.repositories.impls.DefaultUserRepository
import javax.inject.Singleton

@Module
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun providesReceiptsRepository(repo: DefaultReceiptsRepository): ReceiptsRepository

    @Singleton
    @Binds
    abstract fun providesUserRepository(repo: DefaultUserRepository): UserRepository
}