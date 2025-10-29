package week1.data

import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.chat.ToolCall
import com.aallam.openai.client.OpenAI
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import params
import week1.data.datasource.availableFunctions
import week1.domain.dto.request.OpenAiRequest
import week1.domain.dto.response.OpenAiResponse
import week1.domain.key.openAiKey
import week1.domain.repository.OpenAiRepository

/* 구현체 */
class OpenAiRepositoryImpl : OpenAiRepository {

    override suspend fun callOpenAi(request: OpenAiRequest): OpenAiResponse {

        val openAI = OpenAI(
            token = openAiKey
        )

        val chatMessages = mutableListOf(
            ChatMessage(
                role = ChatRole.System,
                content = "안녕! 질문이 있어"
            ),
            ChatMessage(
                role = ChatRole.User,
                content = request.question
            )
        )

        val response = openAI.chatCompletion(
            request = ChatCompletionRequest(
                model = request.model,
                messages = chatMessages,
                functions = params,
            )
        )


        println(response.choices)


        val message = response.choices.first().message
        val finishReason = response.choices.first().finishReason

        if ((finishReason?.value ?: throw RuntimeException("Fail call open ai")) in setOf("tool_calls", "function_call")) {
            val functionCall = message.toolCalls?.firstOrNull() as? ToolCall.Function
                ?: return OpenAiResponse("함수 호출 정보가 없습니다.")

            val functionName = functionCall.function.name
            val argumentsJson = Json.parseToJsonElement(functionCall.function.arguments).jsonObject

            println(argumentsJson.toString())

            val argsMap = argumentsJson.toStringMap()
            val answer = availableFunctions(functionName, argsMap)
            return OpenAiResponse(answer)
        }

        throw RuntimeException("Error")
    }

    fun JsonObject.toStringMap(): Map<String, String> =
        this.mapValues { it.value.toString().trim('"') }
}