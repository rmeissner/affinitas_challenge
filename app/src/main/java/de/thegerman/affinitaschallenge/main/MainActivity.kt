package de.thegerman.affinitaschallenge.main

import de.thegerman.affinitaschallenge.R
import de.thegerman.affinitaschallenge.core.ChallengeApplication
import de.thegerman.affinitaschallenge.core.base.ViewHolderActivity
import de.thegerman.onekay.core.base.ContextProxyModule
import kotlinx.android.synthetic.main.main_layout.*


class MainActivity: ViewHolderActivity<MainViewHolder.Binding, MainViewHolder>() {

    override fun getLayoutID(): Int {
        return R.layout.main_layout
    }

    override fun createBinding(): MainViewHolder.Binding {
        return MainViewHolder.Binding(toolbar, list)
    }

    override fun inject() {
        DaggerMainComponent.builder()
                .appComponent(ChallengeApplication.appComponent(this))
                .contextProxyModule(ContextProxyModule(this))
                .build().inject(this)
    }
}