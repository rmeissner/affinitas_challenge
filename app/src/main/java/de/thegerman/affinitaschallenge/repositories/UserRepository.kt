package de.thegerman.affinitaschallenge.repositories

import de.thegerman.affinitaschallenge.repositories.models.User
import io.reactivex.Observable


interface UserRepository {
    fun login(username: String, password: String): Observable<User>
}