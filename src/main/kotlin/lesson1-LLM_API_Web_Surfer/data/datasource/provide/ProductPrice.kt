package `lesson1-LLM_API_Web_Surfer`.data.datasource.provide

import com.google.gson.Gson
import org.jsoup.Jsoup
import `lesson1-LLM_API_Web_Surfer`.data.datasource.provide.google.GoogleSearchResult
import `lesson1-LLM_API_Web_Surfer`.data.datasource.provide.google.requestGoogle

fun productPrice(args: Map<String, String>): String {
    // args에서 "productName"과 "shopName"을 가져옴
    val productName = args["productName"] ?: throw IllegalArgumentException("Product name is required.")
    val shopName = args["shopName"] ?: throw IllegalArgumentException("Shop name is required.")

    // Google Custom Search API를 이용해 "상점명 + 상품명"으로 검색 요청
    val information = requestGoogle("$shopName 의 $productName")

    // JSON 응답을 GoogleSearchResult 객체로 변환
    val result = Gson().fromJson(information, GoogleSearchResult::class.java)

    // 검색 결과 리스트 순회
    for (item in result.items) {
        // 각 검색 결과 링크의 HTML 데이터를 Jsoup으로 파싱
        val webData = getHtmlData(item.link!!)

        // HTML의 <title> 값이 "상품명 - 상점명"과 정확히 일치하면 해당 페이지가 목표 상품 페이지로 판단
        if (webData["title"].contentEquals("$productName - $shopName")) {
            val price = "${item.pageMap?.metaTags!!.first().currency} ${item.pageMap.metaTags.first().amount}원"
            return price
        }
    }

    // 조건에 맞는 상품 정보를 찾지 못한 경우 예외 발생
    throw RuntimeException("Price not found")
}

// 특정 URL의 HTML 페이지를 가져와 title과 description을 추출하는 함수
private fun getHtmlData(url: String): Map<String, String> {
    // Jsoup으로 해당 URL에 접속해 HTML 문서 파싱
    val doc = Jsoup.connect(url).get()

    val title = doc.title()
    val description = doc.select("meta[name=description]").attr("content")

    // title과 description을 Map 형태로 반환
    return mapOf("title" to title, "description" to description)
}