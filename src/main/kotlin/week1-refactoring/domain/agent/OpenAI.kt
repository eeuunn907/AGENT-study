package `week1-refactoring`.domain.agent

import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.chat.ToolCall
import com.aallam.openai.client.OpenAI
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import `week1-refactoring`.domain.llm.model.LLMRequest
import `week1-refactoring`.domain.llm.model.LLMResponse
import `week1-refactoring`.domain.presentation.LLMRepository
import `week1-refactoring`.domain.search.model.params
import `week1-refactoring`.domain.search.model.provideFunction
import `week1-refactoring`.global.key.openAiKey

class OpenAI : LLMRepository {

    override suspend fun callOpenAi(request: LLMRequest): LLMResponse {

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
                ?: return LLMResponse("함수 호출 정보가 없습니다.")

            val functionName = functionCall.function.name
            val argumentsJson = Json.parseToJsonElement(functionCall.function.arguments).jsonObject

            println(argumentsJson.toString())

            val argsMap = argumentsJson.toStringMap()

            val answer = provideFunction(functionName, argsMap)
            return LLMResponse(answer)
        }

        throw RuntimeException("Error")
    }

    fun JsonObject.toStringMap(): Map<String, String> =
        this.mapValues { it.value.toString().trim('"') }
}
