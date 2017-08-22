package de.thegerman.affinitaschallenge.networking.transformation

import android.graphics.*
import android.graphics.Bitmap.Config
import com.squareup.picasso.Transformation

// enables hardware accelerated rounded corners
// original idea here : http://www.curious-creature.org/2012/12/11/android-recipe-1-image-with-rounded-corners/
class CircleTransformation(private val mPreventRecycle: Boolean) : Transformation {

    override fun transform(source: Bitmap?): Bitmap? {
        if (source == null) {
            return null
        }


        val width = source.width
        val height = source.height
        val dimension = Math.min(width, height)

        val x = (width - dimension) / 2
        val y = (height - dimension) / 2

        val squaredBitmap = Bitmap.createBitmap(source, x, y, dimension, dimension)
        if (squaredBitmap != source && !mPreventRecycle) {
            source.recycle()
        }

        val paint = Paint()
        paint.shader = BitmapShader(squaredBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.isAntiAlias = true

        val radius = dimension / 2f
        val output = Bitmap.createBitmap(dimension, dimension, Config.ARGB_8888)
        val canvas = Canvas(output)
        canvas.drawCircle(radius, radius, radius, paint)

        if (source != output && !mPreventRecycle) {
            source.recycle()
        }

        return output
    }

    override fun key(): String {
        return "rounded"
    }
}