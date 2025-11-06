package lesson1.data

import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.chat.ToolCall
import com.aallam.openai.client.OpenAI
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import params
import lesson1.data.datasource.availableFunctions
import lesson1.domain.dto.request.OpenAiRequest
import lesson1.domain.dto.response.OpenAiResponse
import lesson1.domain.key.openAiKey
import lesson1.domain.repository.OpenAiRepository

/* 구현체 */
class OpenAiRepositoryImpl : OpenAiRepository {

    override suspend fun callOpenAi(request: OpenAiRequest): OpenAiResponse {

        // OpenAI 클라이언트 초기화
        val openAI = OpenAI(
            token = openAiKey
        )

        // 대화 메시지 구성 (시스템 메시지 + 사용자 질문)
        val chatMessages = mutableListOf(
            ChatMessage(
                role = ChatRole.System,
                content = "안녕! 질문이 있어" // GPT에게 기본 컨텍스트 제공
            ),
            ChatMessage(
                role = ChatRole.User,
                content = request.question // 실제 사용자의 질문 내용
            )
        )

        // ChatCompletion API 호출
        val response = openAI.chatCompletion(
            request = ChatCompletionRequest(
                model = request.model,
                messages = chatMessages,
                functions = params,
            )
        )


        println(response.choices)


        // 첫 번째 응답 메시지 및 종료 이유 추출
        val message = response.choices.first().message
        val finishReason = response.choices.first().finishReason

        // GPT가 함수 호출을 요청한 경우 처리
        if ((finishReason?.value ?: throw RuntimeException("Fail call open ai")) in setOf("tool_calls", "function_call")) {

            // GPT 응답 중 함수 호출 정보 추출
            val functionCall = message.toolCalls?.firstOrNull() as? ToolCall.Function
                ?: return OpenAiResponse("함수 호출 정보가 없습니다.")

            // 호출된 함수 이름 및 전달된 인자(JSON) 추출
            val functionName = functionCall.function.name
            val argumentsJson = Json.parseToJsonElement(functionCall.function.arguments).jsonObject

            println(argumentsJson.toString())

            // JSON 인자를 Map<String, String> 형태로 변환
            val argsMap = argumentsJson.toStringMap()

            // 내부에서 정의된 실제 함수 실행 (get_link, product_price, service_description)
            val answer = availableFunctions(functionName, argsMap)
            return OpenAiResponse(answer)
        }

        // 함수 호출이 아닌 일반 응답일 경우 에러 처리
        throw RuntimeException("Error")
    }

    // JsonObject → Map<String, String> 변환 확장 함수
    fun JsonObject.toStringMap(): Map<String, String> =
        this.mapValues { it.value.toString().trim('"') }
}