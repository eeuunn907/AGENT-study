package `lesson1-LLM_API_Web_Surfer`.data.datasource.provide

import com.google.gson.Gson
import `lesson1-LLM_API_Web_Surfer`.data.datasource.provide.google.GoogleSearchResult
import `lesson1-LLM_API_Web_Surfer`.data.datasource.provide.google.requestGoogle

fun getLink(args: Map<String, Any>): String {
    // args 맵에서 "keyword" 값을 가져옴
    val keyword = args["keyword"] ?: throw IllegalArgumentException("Keyword is required.")

    // Google Custom Search API 요청 실행 → JSON 형태의 응답 문자열 반환
    val response = requestGoogle(keyword.toString())

    // JSON 응답 문자열을 GoogleSearchResult 데이터 클래스 구조로 변환
    // Gson 라이브러리를 사용해 JSON을 Kotlin 객체로 역직렬화(deserialize)
    val result = Gson().fromJson(response, GoogleSearchResult::class.java).items

    return result.joinToString("\n") { "${it.title}: ${it.link}" }
}