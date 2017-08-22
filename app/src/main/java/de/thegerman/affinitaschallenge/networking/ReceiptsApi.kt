package de.thegerman.affinitaschallenge.networking

import de.thegerman.affinitaschallenge.networking.models.Receipt
import io.reactivex.Observable


interface ReceiptsApi {
    fun load(): Observable<List<Receipt>>
}