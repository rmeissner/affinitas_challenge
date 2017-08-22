package de.thegerman.affinitaschallenge.core.managers.impls

import android.support.design.widget.Snackbar
import android.view.View
import de.thegerman.affinitaschallenge.core.managers.InAppNotificationManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SnackbarInAppNotificationManager @Inject constructor() : InAppNotificationManager {
    override fun showNotification(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }
}