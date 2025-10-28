package week1.data.datasource.provide.google

import com.google.gson.annotations.SerializedName

data class GoogleSearchResult(val items: List<GoogleSearchItem>)

data class GoogleSearchItem(val title: String?, val link: String?, @SerializedName("pagemap") val pageMap: PageMap?)

data class PageMap(@SerializedName("metatags") val metaTags: List<MetaTags>)

data class MetaTags(
    @SerializedName("product:price:amount") val amount: String,
    @SerializedName("product:price:currency") val currency: String
)