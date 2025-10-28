package week1.data.datasource.provide

import com.google.gson.Gson
import org.jsoup.Jsoup
import week1.data.datasource.provide.google.GoogleSearchResult
import week1.data.datasource.provide.google.requestGoogle

fun productPrice(args: Map<String, String>): String {
    val productName = args["productName"] ?: throw IllegalArgumentException("Product name is required.")
    val shopName = args["shopName"] ?: throw IllegalArgumentException("Shop name is required.")

    val information = requestGoogle("$shopName 의 $productName")

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