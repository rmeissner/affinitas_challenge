package de.thegerman.affinitaschallenge.networking

import de.thegerman.affinitaschallenge.networking.models.LoginRequest
import de.thegerman.affinitaschallenge.networking.models.User
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

// We use this API to demonstrate how we would interact with an API using retrofit
interface AffinitasApi {

    // If we need some special headers we can add them via @Headers (method annotation) or @Header (parameter annotation)
    @POST("login")
    fun login(@Body payload: LoginRequest): Observable<User>

    companion object {
        const val SERVER_URL = "https://api.affinitas.com/"
    }
}