package de.thegerman.affinitaschallenge.main

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import de.thegerman.affinitaschallenge.R
import de.thegerman.affinitaschallenge.core.ChallengeLog
import de.thegerman.affinitaschallenge.core.adapter.BaseViewHolder
import de.thegerman.affinitaschallenge.networking.ImageLoader
import de.thegerman.affinitaschallenge.repositories.ReceiptsRepository
import de.thegerman.affinitaschallenge.repositories.entries.ReceiptEntry
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable


class ReceiptViewHolder(val binding: Binding, val imageLoader: ImageLoader,
                        val repository: ReceiptsRepository) :
        BaseViewHolder<ReceiptEntry>(binding.root, ReceiptEntry::class.java) {

    private val disposables = CompositeDisposable()

    private var entry: ReceiptEntry? = null

    override fun bindCasted(entry: ReceiptEntry, payloads: List<Any>?) {
        this.entry = entry
        binding.rate1Button.setOnClickListener { rate(1) }
        binding.rate2Button.setOnClickListener { rate(2) }
        binding.rate3Button.setOnClickListener { rate(3) }
        binding.rate4Button.setOnClickListener { rate(4) }
        binding.rate5Button.setOnClickListener { rate(5) }
        disposables.addAll(
                repository.observeFavorite(entry.receipt.id)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::updateFavorited, ChallengeLog::log),
                repository.observeRating(entry.receipt.id)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::updateRating, ChallengeLog::log)
        )
        updateFavorited(false)
        updateRating(0)

        binding.title.text = entry.receipt.name
        imageLoader.load(imageLoader.request(binding.image, entry.receipt.image))

        // When we extract more information it is possible to adjust the Binding, so that we can display them
    }

    private fun rate(rating: Int) {
        updateRating(rating)
        // We could also show a spinner here
        entry?.let { repository.rate(it.receipt.id, rating) }
    }

    private fun favorite(favorited: Boolean) {
        updateFavorited(favorited)
        // We could also show a spinner here
        entry?.let { repository.favorite(it.receipt.id, favorited) }
    }

    private fun updateFavorited(favorited: Boolean) {
        binding.favoriteButton.setOnClickListener { favorite(!favorited) }
        binding.favoriteButton.setImageResource(
                if (favorited) R.drawable.ic_favorite
                else R.drawable.ic_favorite_border
        )
    }

    private fun updateRating(rating: Int) {
        setRatingImage(binding.rate1Button, rating > 0)
        setRatingImage(binding.rate2Button, rating > 1)
        setRatingImage(binding.rate3Button, rating > 2)
        setRatingImage(binding.rate4Button, rating > 3)
        setRatingImage(binding.rate5Button, rating > 4)
    }

    private fun setRatingImage(view: ImageView, active: Boolean) {
        view.setImageResource(
                if (active) R.drawable.ic_star
                else R.drawable.ic_star_border
        )
    }

    override fun unbind() {
        imageLoader.cancel(binding.image)
        disposables.clear()
        super.unbind()
    }

    class Binding(val root: View, val title: TextView, val image: ImageView, val favoriteButton: ImageView,
                  val rate1Button: ImageView, val rate2Button: ImageView, val rate3Button: ImageView,
                  val rate4Button: ImageView, val rate5Button: ImageView)

}