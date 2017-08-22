package de.thegerman.affinitaschallenge.networking.utils

import android.content.Context
import android.os.StatFs
import de.thegerman.affinitaschallenge.core.utils.AppUtils
import de.thegerman.affinitaschallenge.core.utils.FileUtils
import retrofit2.HttpException
import java.io.File

object NetworkingUtils {

    private const val MIN_DISK_CACHE_SIZE = 5 * 1024 * 1024
    private const val MAX_DISK_CACHE_SIZE = 50 * 1024 * 1024
    private const val FOLDER_PICASSO_CACHE = "picasso-cache/"
    private const val FOLDER_REQUEST_CACHE = "request-cache/"

    fun calculateDiskCacheSize(dir: File): Long {
        var size = MIN_DISK_CACHE_SIZE.toLong()

        try {
            val statFs = StatFs(dir.absolutePath)
            val available = statFs.blockCount.toLong() * statFs.blockSize
            // Target 2% of the total space.
            size = available / 50
        } catch (ignored: IllegalArgumentException) {
        }

        // Bound inside min/max size for disk cache.
        return Math.max(Math.min(size, MAX_DISK_CACHE_SIZE.toLong()), MIN_DISK_CACHE_SIZE.toLong())
    }

    fun getPicassoCacheDir(context: Context): File {
        return FileUtils.checkFolder(File(context.cacheDir, FOLDER_PICASSO_CACHE))
    }

    fun getRequestCacheDir(context: Context): File {
        return FileUtils.checkFolder(File(context.cacheDir, FOLDER_REQUEST_CACHE))
    }

    fun getErrorMessage(it: Throwable?): String? {
        if (!AppUtils.isDebug) {
            // Never expose internal error message in the release app
            return null
        }
        val httpError = it as? HttpException ?: return null
        return httpError.response().errorBody()?.string().toString()
    }
}