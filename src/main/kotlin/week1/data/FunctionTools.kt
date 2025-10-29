import com.aallam.openai.api.chat.ChatCompletionFunction
import com.aallam.openai.api.core.Parameters
import kotlinx.serialization.json.*


// 사용할 함수 정의
val params = listOf(
    ChatCompletionFunction(
        name = "get_link", // 함수 이름 (OpenAI가 호출할 이름)
        description = "I want to get links related to software development.", // 함수 목적 설명
        parameters = Parameters.buildJsonObject {
            put("type", "object") // 전달되는 파라미터의 타입은 객체(object)
            putJsonObject("properties") {// 함수에서 요구하는 파라미터들 정의
                putJsonObject("keyword") {
                    put("type", "string") // 문자열 타입
                }
            }
            putJsonArray("required") {// 필수 파라미터 목록
                add(JsonPrimitive("keyword"))
            }
        }
    ),
    ChatCompletionFunction(
        name = "product_price",
        description = "I want to get the price of a product.", // 상품 가격을 가져오기 위한 함수
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
            // 두 필드는 모두 필수(required)
            putJsonArray("required") {
                add(JsonPrimitive("shopName"))
                add(JsonPrimitive("productName"))
            }
        }
    ),
    ChatCompletionFunction(
        name = "service_description",
        description = "I want service information explained.", // 회사의 서비스 설명을 받고 싶을 때 사용
        parameters = Parameters.buildJsonObject {
            put("type", "object")
            putJsonObject("properties") {
                // 회사명
                putJsonObject("companyName") {
                    put("type", "string")
                }
                // 서비스명
                putJsonObject("serviceNames") {
                    put("type", "array")
                    putJsonObject("items") {
                        put("type", "string")
                    }
                }
            }
            // companyName, serviceNames 모두 필수
            putJsonArray("required") {
                add(JsonPrimitive("companyName"))
                add(JsonPrimitive("serviceNames"))
            }
        }
    ),
)