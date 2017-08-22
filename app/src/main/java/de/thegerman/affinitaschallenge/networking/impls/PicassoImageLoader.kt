package de.thegerman.affinitaschallenge.networking.impls

import android.content.Context
import android.widget.ImageView
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import de.thegerman.affinitaschallenge.networking.ImageLoader
import de.thegerman.affinitaschallenge.networking.ImageLoader.RequestBuilder
import de.thegerman.affinitaschallenge.networking.NetworkingModule.Companion.PARAM_PICASSO_CLIENT
import okhttp3.OkHttpClient
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class PicassoImageLoader @Inject constructor(context: Context,
                                             @Named(PARAM_PICASSO_CLIENT) client: OkHttpClient) :
        ImageLoader {

    private val internalLoader: Picasso = Picasso.Builder(context)
            .downloader(OkHttp3Downloader(client))
            .build()

    override fun cancel(view: ImageView) {
        internalLoader.cancelRequest(view)
    }

    override fun request(view: ImageView, url: String): RequestBuilder {
        return UrlRequestBuilder(view, url)
    }

    override fun load(requestBuilder: RequestBuilder) {
        (requestBuilder as? PicassoRequestBuilder)?.perform(internalLoader)
    }

    abstract class PicassoRequestBuilder : RequestBuilder() {

        protected abstract fun create(picasso: Picasso): RequestCreator

        fun perform(picasso: Picasso) {
            val request = create(picasso)
            if (transformation != null) {
                request.transform(transformation!!)
            }
            if (placeholder != 0) {
                request.placeholder(placeholder)
            }
            internalPerform(request)
        }

        abstract fun internalPerform(request: RequestCreator)
    }

    private abstract class ImageRequestBuilder(private val target: ImageView) : PicassoRequestBuilder() {
        override fun internalPerform(request: RequestCreator) {
            request.into(target)
        }
    }

    private class UrlRequestBuilder(target: ImageView, private val url: String) : ImageRequestBuilder(target) {
        override fun create(picasso: Picasso): RequestCreator {
            return picasso.load(url)
        }
    }
}
