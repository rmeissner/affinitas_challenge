package de.thegerman.affinitaschallenge.core

import android.app.Application
import android.content.Context
import dagger.Component
import de.thegerman.affinitaschallenge.core.di.CoreModule
import de.thegerman.affinitaschallenge.networking.NetworkingModule
import de.thegerman.affinitaschallenge.repositories.RepositoryModule
import de.thegerman.onekay.core.di.AppComponent
import de.thegerman.onekay.core.di.AppModule
import de.thegerman.onekay.storage.StorageModule
import javax.inject.Singleton

// We allow extension of this class for testing purpose. This is not optimal but the easiest way to
// inject mocks for espresso tests.
open class ChallengeApplication : Application() {

    private lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        component = buildComponent()

        component.inject(this)
    }

    open fun buildComponent(): AppComponent {
        return DaggerChallengeApplication_ProductionComponent.builder()
                .appModule(AppModule(this)).build()
    }

    @Singleton
    @Component(modules = arrayOf(AppModule::class, CoreModule::class, NetworkingModule::class,
            RepositoryModule::class, StorageModule::class))
    interface ProductionComponent : AppComponent

    companion object {
        fun application(context: Context): ChallengeApplication {
            return context.applicationContext as ChallengeApplication
        }

        fun appComponent(context: Context): AppComponent {
            return application(context).component
        }
    }
}