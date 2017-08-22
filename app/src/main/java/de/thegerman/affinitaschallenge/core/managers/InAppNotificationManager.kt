package de.thegerman.affinitaschallenge.core.managers

import android.view.View


interface InAppNotificationManager {
    fun showNotification(view: View, message: String)
}