package apps.flotrust.starwarswarriors.data.network

import android.content.Context
import apps.flotrust.starwarswarriors.domain.model.Warrior
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import kotlin.coroutines.resume

class NetworkClient {    // класс, который является проводником в мир необходимой нам информации, которая хранится в недрах гитхаба

    private val client = OkHttpClient.Builder().build() // окhttp клиент для работы с сетью

    suspend fun getWarriorsInfo():List<Warrior>?{
        val request = Request.Builder().get().url("https://raw.githubusercontent.com/meganiger/db/refs/heads/main/info").build()
        // гет запрос на сервер
       return suspendCancellableCoroutine { continuation->  // продолжаем асинхронно
           client.newCall(request).enqueue(
               object : Callback {
                   override fun onFailure(call: Call, e: IOException) {
                       continuation.resume(null)
                   }

                   override fun onResponse(call: Call, response: Response) {
                       if (response.isSuccessful) {
                           val responseData = response.body?.string()
                           // Парсинг JSON и преобразование в List<Warrior>
                           val warriors = Json.decodeFromString<List<Warrior>>(responseData?:"")
                           continuation.resume(warriors)
                       } else {
                           continuation.resume(null) // Возвращаем null, если ответ не успешный
                       }
                   }
               }
           )
       }


    }


}