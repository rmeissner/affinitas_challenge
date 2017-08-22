package de.thegerman.affinitaschallenge.networking.impls

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import de.thegerman.affinitaschallenge.networking.ReceiptsApi
import de.thegerman.affinitaschallenge.networking.models.Receipt
import io.reactivex.Observable
import java.nio.charset.Charset
import javax.inject.Singleton

@Singleton
class GsonReceiptsApi(
        val context: Context,
        val gson: Gson
): ReceiptsApi {
    override fun load(): Observable<List<Receipt>> {
        return Observable.fromCallable {
            val type = TypeToken.getParameterized(List::class.java, Receipt::class.java)
            gson.fromJson<List<Receipt>>(loadStringFromAssets("recipes.json"), type.type)
        }
    }


    private fun loadStringFromAssets(path: String): String {
        return context.assets.open(path).use {
            it.readBytes().toString(Charset.defaultCharset())
        }
    }
}