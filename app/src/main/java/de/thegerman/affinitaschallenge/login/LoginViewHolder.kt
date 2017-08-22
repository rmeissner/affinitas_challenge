package de.thegerman.affinitaschallenge.login

import android.content.Context
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import de.thegerman.affinitaschallenge.R
import de.thegerman.affinitaschallenge.core.ChallengeLog
import de.thegerman.affinitaschallenge.core.base.ViewHolder
import de.thegerman.affinitaschallenge.core.managers.InAppNotificationManager
import de.thegerman.affinitaschallenge.core.utils.RecyclerViewUtils
import de.thegerman.affinitaschallenge.main.MainViewModel
import de.thegerman.affinitaschallenge.main.ReceiptsAdapter
import de.thegerman.onekay.core.base.ContextProxy
import de.thegerman.onekay.core.di.ForView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@ForView
class LoginViewHolder @Inject constructor(
        val context: Context,
        val proxy: ContextProxy,
        val inAppNotificationManager: InAppNotificationManager,
        val viewModel: LoginViewModel
) : ViewHolder<LoginViewHolder.Binding> {

    var disposables = CompositeDisposable()
    var binding: Binding? = null

    override fun bind(binding: Binding) {
        this.binding = binding
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.toolbar.setNavigationOnClickListener {
            proxy.finish()
        }
        binding.toolbar.setTitle(R.string.login_title)
        binding.loginButton.setOnClickListener {
            this.binding?.let {
                viewModel.login(it.usernameInput.text.toString(), it.passwordInput.text.toString())
            }
        }
        disposables.addAll(
                viewModel.observeLoading()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::updateLoading, ChallengeLog::log),
                viewModel.observeNotifications()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::showNotification, ChallengeLog::log)
        )
    }

    private fun updateLoading(loading: Boolean) {
        binding?.let {
            it.inputContainer.visibility = if (loading) View.GONE else View.VISIBLE
            it.loginButton.visibility = if (loading) View.GONE else View.VISIBLE
            it.loginProgress.visibility = if (!loading) View.GONE else View.VISIBLE
        }
    }

    private fun showNotification(notification: String) {
        binding?.let {
            inAppNotificationManager.showNotification(it.root, notification)
        }
    }

    override fun start() {
        // noop
    }

    override fun stop() {
        viewModel.inactive()
    }

    override fun unbind() {
        disposables.clear()
    }

    data class Binding(val root: ViewGroup, val inputContainer: ViewGroup, val toolbar: Toolbar,
                       val usernameInput: EditText, val passwordInput: EditText,
                       val loginButton: View, val loginProgress: View)
}