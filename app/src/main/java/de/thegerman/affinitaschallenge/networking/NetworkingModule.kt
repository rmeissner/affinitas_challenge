package de.thegerman.affinitaschallenge.networking

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import de.thegerman.affinitaschallenge.networking.impls.GsonReceiptsApi
import de.thegerman.affinitaschallenge.networking.impls.PicassoImageLoader
import de.thegerman.affinitaschallenge.networking.utils.NetworkingUtils
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkingModule {
    @Provides
    @Singleton
    fun providesGson(): Gson {
        // Here we can add custom type adapters (e.g. for durations or timestamps)
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    @Named(PARAM_PICASSO_CLIENT)
    fun providesPicassoClient(context: Context): OkHttpClient {
        val requestCacheDir = NetworkingUtils.getPicassoCacheDir(context)
        val clientBuilder = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .cache(Cache(requestCacheDir, NetworkingUtils.calculateDiskCacheSize(requestCacheDir)))
        return clientBuilder.build()
    }

    @Provides
    @Singleton
    @Named(PARAM_SERVICE_CLIENT)
    fun providesServiceClient(context: Context): OkHttpClient {
        // Here we could inject custom interceptors (e.g. add authentication header, or log network requests)
        val requestCacheDir = NetworkingUtils.getPicassoCacheDir(context)
        val clientBuilder = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .cache(Cache(requestCacheDir, NetworkingUtils.calculateDiskCacheSize(requestCacheDir)))
        return clientBuilder.build()
    }

    @Provides
    @Singleton
    fun providesImageLoader(context: Context, @Named(PARAM_PICASSO_CLIENT) client: OkHttpClient):
            ImageLoader {
        return PicassoImageLoader(context, client)
    }

    @Provides
    @Singleton
    fun providesAffinitasApi(@Named(PARAM_SERVICE_CLIENT) client: OkHttpClient):
            AffinitasApi {
        return Retrofit.Builder()
                .baseUrl(AffinitasApi.SERVER_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build().create(AffinitasApi::class.java)
    }

    @Provides
    @Singleton
    fun providesReceiptsApi(context: Context, gson: Gson):
            ReceiptsApi {
        return GsonReceiptsApi(context, gson)
    }

    companion object {
        const val PARAM_SERVICE_CLIENT = "ServiceClient"
        const val PARAM_PICASSO_CLIENT = "PicassoClient"
    }
}