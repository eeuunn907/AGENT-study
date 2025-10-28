package week1.data.datasource.provide.google

import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import week1.domain.key.googleApiKey
import week1.domain.key.googleEngineKey

fun requestGoogle(keyword: String): String {
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