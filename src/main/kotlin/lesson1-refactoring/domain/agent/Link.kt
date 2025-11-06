package `lesson1-refactoring`.domain.agent

import com.google.gson.Gson
import lesson1.data.datasource.provide.google.GoogleSearchResult
import lesson1.data.datasource.provide.google.requestGoogle

fun link(args: Map<String, Any>): String {
    val keyword = args["keyword"] ?: throw IllegalArgumentException("Keyword is required.")

    val response = requestGoogle(keyword.toString())

    val result = Gson().fromJson(response, GoogleSearchResult::class.java).items

    return result.joinToString("\n") { "${it.title}: ${it.link}" }
}
