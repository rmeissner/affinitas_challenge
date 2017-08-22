package de.thegerman.affinitaschallenge.repositories.impls

import de.thegerman.affinitaschallenge.networking.AffinitasApi
import de.thegerman.affinitaschallenge.networking.models.LoginRequest
import de.thegerman.affinitaschallenge.repositories.UserRepository
import de.thegerman.affinitaschallenge.repositories.models.User
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class DefaultUserRepository @Inject constructor(val api: AffinitasApi) : UserRepository {
    override fun login(username: String, password: String): Observable<User> {
        return api.login(LoginRequest(username, password)).map {
            User(it.email, it.name, it.latlng)
        }
    }
}