package de.thegerman.affinitaschallenge.networking

import android.widget.ImageView
import com.squareup.picasso.Transformation


interface ImageLoader {
    fun cancel(view: ImageView)
    fun request(view: ImageView, url: String): RequestBuilder
    fun load(requestBuilder: RequestBuilder)

    abstract class RequestBuilder {

        protected var transformation: Transformation? = null
        protected var placeholder: Int = 0

        open fun transformation(transformation: Transformation?): RequestBuilder {
            this.transformation = transformation
            return this
        }

        open fun placeholder(placeholder: Int): RequestBuilder {
            this.placeholder = placeholder
            return this
        }
    }
}