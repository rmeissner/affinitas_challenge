package de.thegerman.affinitaschallenge.main

import android.content.Context
import android.support.v7.util.DiffUtil
import com.jakewharton.rxrelay2.BehaviorRelay
import de.thegerman.affinitaschallenge.core.ChallengeLog
import de.thegerman.affinitaschallenge.core.adapter.AdapterData
import de.thegerman.affinitaschallenge.core.adapter.AdapterUpdate
import de.thegerman.affinitaschallenge.login.LoginActivity
import de.thegerman.affinitaschallenge.repositories.ReceiptsRepository
import de.thegerman.affinitaschallenge.repositories.entries.ReceiptEntry
import de.thegerman.onekay.core.base.ContextProxy
import de.thegerman.onekay.core.di.ForView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@ForView
class MainViewModel @Inject constructor(
        val proxy: ContextProxy,
        val context: Context,
        val repository: ReceiptsRepository
) {

    val disposables = CompositeDisposable()
    val receiptsRelay = BehaviorRelay.create<List<ReceiptEntry>>()

    fun observeReceipts(): Observable<AdapterUpdate<ReceiptEntry>> {
        return receiptsRelay
                .map { AdapterUpdate(AdapterData(entries = it)) }
                .scan { (previousData), (newData) ->
                    val diffCallback = AdapterUpdate.DiffCallback(previousData, newData)
                    val diffResult = DiffUtil.calculateDiff(diffCallback)
                    AdapterUpdate(newData, previousData.uuid, diffResult)
                }
                .subscribeOn(Schedulers.io())
    }

    fun active() {
        disposables.add(
                repository.loadReceipts()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(receiptsRelay::accept, ChallengeLog::log)
        )
    }

    fun inactive() {
        disposables.clear()
    }

    fun showLogin(): Boolean {
        proxy.startActivity(LoginActivity.createIntent(context))
        return true
    }
}