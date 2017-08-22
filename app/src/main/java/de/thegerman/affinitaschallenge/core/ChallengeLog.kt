package de.thegerman.affinitaschallenge.core


object ChallengeLog {
    fun log(t: Throwable) {
        t.printStackTrace()
    }
}