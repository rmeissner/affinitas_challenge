package de.thegerman.onekay.core.base

import android.content.Intent
import dagger.Module
import dagger.Provides
import de.thegerman.onekay.core.di.ForView

@Module
class ContextProxyModule(val proxy: ContextProxy) {

    @Provides
    @ForView
    fun providesContextProxy(): ContextProxy {
        return proxy
    }
}

interface ContextProxy {
    fun startActivity(intent: Intent)

    fun finish()
}