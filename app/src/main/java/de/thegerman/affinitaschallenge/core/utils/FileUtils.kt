package de.thegerman.affinitaschallenge.core.utils

import android.content.Context
import java.io.File

object FileUtils {
    fun getDirectory(context: Context, name: String): File {
        return checkFolder(File(context.filesDir, name))
    }

    fun checkFolder(f: File): File {
        if (!f.exists() || !f.isDirectory) {
            f.mkdirs()
        }
        return f
    }
}