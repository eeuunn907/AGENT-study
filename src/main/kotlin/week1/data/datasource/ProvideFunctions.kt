@file:Suppress("PLUGIN_IS_NOT_ENABLED")

package week1.data.datasource

import week1.data.datasource.provide.getLink
import week1.data.datasource.provide.productPrice
import week1.data.datasource.provide.serviceDescription

fun availableFunctions(functionName: String, value: Map<String, String>): String {
    return when (functionName) {
        "get_link" -> getLink(value)
        "product_price" -> productPrice(value)
        "service_description" -> serviceDescription(value)
        else -> throw RuntimeException("function name is incorrect")
    }
}