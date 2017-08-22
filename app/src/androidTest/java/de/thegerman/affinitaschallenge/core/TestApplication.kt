package de.thegerman.affinitaschallenge.core

import android.support.test.InstrumentationRegistry
import dagger.Component
import de.thegerman.affinitaschallenge.core.di.MockedModule
import de.thegerman.onekay.core.di.AppComponent
import de.thegerman.onekay.core.di.AppModule
import javax.inject.Singleton

class TestApplication : ChallengeApplication() {

    @Singleton
    @Component(modules = arrayOf(AppModule::class, MockedModule::class))
    interface TestComponent : AppComponent {
        fun inject(test: BaseTest)
    }

    override fun buildComponent(): AppComponent {
        return DaggerTestApplication_TestComponent.builder()
                .appModule(AppModule(this)).build()
    }

    companion object {

        fun testComponent(): TestComponent {
            val instrumentation = InstrumentationRegistry.getInstrumentation()
            val app = instrumentation.targetContext.applicationContext as TestApplication
            return appComponent(app) as TestComponent
        }
    }
}