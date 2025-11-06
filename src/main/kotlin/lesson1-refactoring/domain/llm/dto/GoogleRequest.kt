package `lesson1-refactoring`.domain.llm.dto

import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import `lesson1-refactoring`.global.key.googleApiKey
import `lesson1-refactoring`.global.key.googleEngineKey

fun googleRequest(keyword: String): String {
    val client = OkHttpClient()

    val url = HttpUrl.Builder()
        .scheme("https")
        .host("www.googleapis.com")
        .addPathSegments("customsearch/v1")
        .addQueryParameter("q", keyword)
        .addQueryParameter("key", googleApiKey)
        .addQueryParameter("cx", googleEngineKey)
        .build()

    val request = Request.Builder()
        .url(url)
        .build()

    client.newCall(request).execute().use { response ->
        val result = response.body.string()
        println("result1: $response")
        return result
    }
}
