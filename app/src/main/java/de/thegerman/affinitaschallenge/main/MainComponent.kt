package de.thegerman.affinitaschallenge.main

import dagger.Component
import de.thegerman.onekay.core.base.ContextProxyModule
import de.thegerman.onekay.core.di.AppComponent
import de.thegerman.onekay.core.di.ForView

@ForView
@Component(modules = arrayOf(ContextProxyModule::class), dependencies = arrayOf(AppComponent::class))
interface MainComponent {
    fun inject(activity: MainActivity)
}