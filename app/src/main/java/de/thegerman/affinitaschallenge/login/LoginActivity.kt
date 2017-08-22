package de.thegerman.affinitaschallenge.login

import android.content.Context
import android.content.Intent
import de.thegerman.affinitaschallenge.R
import de.thegerman.affinitaschallenge.core.ChallengeApplication
import de.thegerman.affinitaschallenge.core.base.ViewHolderActivity
import de.thegerman.onekay.core.base.ContextProxyModule
import kotlinx.android.synthetic.main.login_layout.*

class LoginActivity : ViewHolderActivity<LoginViewHolder.Binding, LoginViewHolder>() {

    override fun getLayoutID(): Int {
        return R.layout.login_layout
    }

    override fun createBinding(): LoginViewHolder.Binding {
        return LoginViewHolder.Binding(container, input_container, toolbar, username_input, password_input, login_button, progress)
    }

    override fun inject() {
        DaggerLoginComponent.builder()
                .appComponent(ChallengeApplication.appComponent(this))
                .contextProxyModule(ContextProxyModule(this))
                .build().inject(this)
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }
}