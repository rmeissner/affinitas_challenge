package de.thegerman.affinitaschallenge.login

import android.content.Context
import com.jakewharton.rxrelay2.BehaviorRelay
import de.thegerman.affinitaschallenge.R
import de.thegerman.affinitaschallenge.core.ChallengeLog
import de.thegerman.affinitaschallenge.core.utils.ValidationUtils
import de.thegerman.affinitaschallenge.repositories.UserRepository
import de.thegerman.onekay.core.base.ContextProxy
import de.thegerman.onekay.core.di.ForView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@ForView
class LoginViewModel @Inject constructor(
        private val proxy: ContextProxy,
        private val context: Context,
        private val repository: UserRepository
) {

    private val disposables = CompositeDisposable()
    private val loadingRelay = BehaviorRelay.create<Boolean>()
    private val notificationRelay = BehaviorRelay.create<String>()

    fun observeLoading(): Observable<Boolean> {
        return loadingRelay
    }

    fun observeNotifications(): Observable<String> {
        return notificationRelay
    }

    fun login(username: String, password: String) {
        disposables.add(
                Observable.fromCallable {
                    val cleanPassword = password.trim()
                    val cleanUsername = username.trim()
                    if (!ValidationUtils.isValidEmail(cleanUsername)) {
                        // We should probably use a custom exception here (e.g. InvalidEmailException)
                        throw IllegalArgumentException(context.getString(R.string.invalid_email))
                    }
                    if (cleanPassword.length < 8) {
                        // Same here (e.g. PasswordTooShortException)
                        throw IllegalArgumentException(context.getString(R.string.password_too_short))
                    }
                    Pair(cleanUsername, cleanPassword)
                }
                        .flatMap { (username, password) ->
                            repository.login(username, password)
                        }
                        .doOnSubscribe { loadingRelay.accept(true) }
                        .doAfterTerminate { loadingRelay.accept(false) }
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            // Successfully logged in, close the login screen
                            proxy.finish()
                        }, {
                            val msg = (it as? IllegalArgumentException)?.message ?:
                                    context.getString(R.string.unknown_error)
                            notificationRelay.accept(msg)
                            ChallengeLog.log(it)
                        })
        )
    }

    fun inactive() {
        disposables.clear()
    }
}