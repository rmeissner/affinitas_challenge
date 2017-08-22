package de.thegerman.affinitaschallenge.login

import dagger.Component
import de.thegerman.onekay.core.base.ContextProxyModule
import de.thegerman.onekay.core.di.AppComponent
import de.thegerman.onekay.core.di.ForView

@ForView
@Component(modules = arrayOf(ContextProxyModule::class), dependencies = arrayOf(AppComponent::class))
interface LoginComponent {
    fun inject(activity: LoginActivity)
}