package de.thegerman.affinitaschallenge.networking.impls

import de.thegerman.affinitaschallenge.networking.AffinitasApi
import de.thegerman.affinitaschallenge.networking.models.LoginRequest
import de.thegerman.affinitaschallenge.networking.models.User
import io.reactivex.Observable

class TestAffinitasApi: AffinitasApi {
    override fun login(payload: LoginRequest): Observable<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}