package `lesson1-LLM_API_Web_Surfer`.data.datasource.provide.google

import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import `lesson1-LLM_API_Web_Surfer`.domain.key.googleApiKey
import `lesson1-LLM_API_Web_Surfer`.domain.key.googleEngineKey

//
fun requestGoogle(keyword: String): String {
    // OkHttpClient 객체 생성 (HTTP 요청을 보내기 위한 클라이언트)
    val client = OkHttpClient()

    // Google Custom Search API 호출용 URL 생성
    val url = HttpUrl.Builder()
        .scheme("https") // HTTPS 프로토콜 사용
        .host("www.googleapis.com") // Google API 기본 도메인
        .addPathSegments("customsearch/v1") // Custom Search API 엔드포인트 경로
        .addQueryParameter("q", keyword) // 검색어 파라미터
        .addQueryParameter("key", googleApiKey) // Google API 인증 키
        .addQueryParameter("cx", googleEngineKey) // Custom Search 엔진 식별 키
        .build()

    // 요청 객체
    val request = Request.Builder()
        .url(url) // 위에서 만든 URL을 요청 대상 URL로 설정
        .build()

    // 응답
    client.newCall(request).execute().use { response ->
        // 응답 본문 body를 문자열로 변환
        val result = response.body.string()
        println("result1: $response")
        return result
    }
}