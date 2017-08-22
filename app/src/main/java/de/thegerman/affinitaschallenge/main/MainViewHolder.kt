package de.thegerman.affinitaschallenge.main

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import de.thegerman.affinitaschallenge.R
import de.thegerman.affinitaschallenge.core.ChallengeLog
import de.thegerman.affinitaschallenge.core.base.ViewHolder
import de.thegerman.affinitaschallenge.core.utils.RecyclerViewUtils
import de.thegerman.onekay.core.di.ForView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@ForView
class MainViewHolder @Inject constructor(
        val context: Context,
        val viewModel: MainViewModel,
        val adapter: ReceiptsAdapter
) : ViewHolder<MainViewHolder.Binding> {

    var disposables = CompositeDisposable()
    var binding: Binding? = null

    override fun bind(binding: Binding) {
        this.binding = binding
        binding.list.adapter = adapter
        binding.list.layoutManager = LinearLayoutManager(context)
        binding.toolbar.setTitle(R.string.receipts_title)
        val loginItem = binding.toolbar.menu.add(R.string.login_title)
        loginItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        loginItem.setOnMenuItemClickListener {
            viewModel.showLogin()
        }
        disposables.add(
                viewModel.observeReceipts()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(adapter::update, ChallengeLog::log)
        )
    }

    override fun start() {
        viewModel.active()
    }

    override fun stop() {
        viewModel.inactive()
    }

    override fun unbind() {
        disposables.clear()
        binding?.let { RecyclerViewUtils.unbindAllViewHolders(it.list) }
    }

    data class Binding(val toolbar: Toolbar, val list: RecyclerView)
}