package `lesson1-LLM_API_Web_Surfer`.data.datasource.provide.google

import com.google.gson.annotations.SerializedName

// Google Custom Search API의 전체 응답을 담는 데이터 클래스
data class GoogleSearchResult(val items: List<GoogleSearchItem>)

// 개별 검색 결과(한 개의 웹페이지)를 표현하는 데이터 클래스
// 페이지에 포함된 구조화된 데이터(pagemap)
data class GoogleSearchItem(val title: String?, val link: String?, @SerializedName("pagemap") val pageMap: PageMap?)

// metatags는 HTML <meta> 태그 정보를 JSON으로 변환한 부분
data class PageMap(@SerializedName("metatags") val metaTags: List<MetaTags>)

// 메타데이터를 담는 데이터 클래스
data class MetaTags(
    @SerializedName("product:price:amount") val amount: String,
    @SerializedName("product:price:currency") val currency: String
)