package `lesson1-refactoring`.domain.search.model

import com.aallam.openai.api.chat.ChatCompletionFunction
import com.aallam.openai.api.core.Parameters
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.put
import kotlinx.serialization.json.putJsonArray
import kotlinx.serialization.json.putJsonObject

val params = listOf(
    ChatCompletionFunction(
        name = "link",
        description = "I want to get links related to software development.",
        parameters = Parameters.buildJsonObject {
            put("type", "object")
            putJsonObject("properties") {
                putJsonObject("keyword") {
                    put("type", "string")
                }
            }
            putJsonArray("required") {
                add(JsonPrimitive("keyword"))
            }
        }
    ),
    ChatCompletionFunction(
        name = "price",
        description = "I want to get the price of a product.",
        parameters = Parameters.buildJsonObject {
            put("type", "object")
            putJsonObject("properties") {
                // 상점명
                putJsonObject("shopName") {
                    put("type", "string")
                }
                // 상품명
                putJsonObject("productName") {
                    put("type", "string")
                }
            }
            putJsonArray("required") {
                add(JsonPrimitive("shopName"))
                add(JsonPrimitive("productName"))
            }
        }
    ),
    ChatCompletionFunction(
        name = "description",
        description = "I want service information explained.",
        parameters = Parameters.buildJsonObject {
            put("type", "object")
            putJsonObject("properties") {
                putJsonObject("companyName") {
                    put("type", "string")
                }
                putJsonObject("serviceNames") {
                    put("type", "array")
                    putJsonObject("items") {
                        put("type", "string")
                    }
                }
            }
            putJsonArray("required") {
                add(JsonPrimitive("companyName"))
                add(JsonPrimitive("serviceNames"))
            }
        }
    ),
)
