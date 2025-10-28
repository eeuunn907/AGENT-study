import com.aallam.openai.api.chat.ChatCompletionFunction
import com.aallam.openai.api.core.Parameters
import kotlinx.serialization.json.*

val params = listOf(
    ChatCompletionFunction(
        name = "get_link",
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
        name = "product_price",
        description = "I want to get the price of a product.",
        parameters = Parameters.buildJsonObject {
            put("type", "object")
            putJsonObject("properties") {
                putJsonObject("shopName") {
                    put("type", "string")
                }
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
        name = "service_description",
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