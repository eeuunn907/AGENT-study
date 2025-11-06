package `lesson1-refactoring`.domain.agent

import com.google.gson.Gson
import org.jsoup.Jsoup
import `lesson1-refactoring`.domain.llm.dto.googleRequest
import `lesson1-refactoring`.domain.search.llm.GoogleSearchResult

fun productPrice(args: Map<String, String>): String {
    val productName = args["productName"] ?: throw IllegalArgumentException("Product name is required.")
    val shopName = args["shopName"] ?: throw IllegalArgumentException("Shop name is required.")

    val information = googleRequest("$shopName 의 $productName")

    val result = Gson().fromJson(information, GoogleSearchResult::class.java)

    for (item in result.items) {
        val webData = getHtmlData(item.link!!)

        if (webData["title"].contentEquals("$productName - $shopName")) {
            val price = "${item.pageMap?.metaTags!!.first().currency} ${item.pageMap.metaTags.first().amount}원"
            return price
        }
    }

    throw RuntimeException("Price not found")
}

private fun getHtmlData(url: String): Map<String, String> {
    val doc = Jsoup.connect(url).get()

    val title = doc.title()
    val description = doc.select("meta[name=description]").attr("content")

    return mapOf("title" to title, "description" to description)
}
