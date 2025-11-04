package `week1-refactoring`.domain.agent

import com.aallam.openai.api.chat.*
import com.aallam.openai.client.OpenAI
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import `week1-refactoring`.domain.llm.dto.model.LLMRequest
import `week1-refactoring`.domain.llm.dto.model.LLMResponse
import `week1-refactoring`.domain.presentation.LLMRepository
import `week1-refactoring`.domain.search.model.params
import `week1-refactoring`.domain.search.model.provideFunction
import `week1-refactoring`.global.key.openAiKey

class OpenAI : LLMRepository {

    override suspend fun callOpenAi(request: LLMRequest): LLMResponse {

        val openAI = OpenAI(
            token = openAiKey
        ) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
        }

        val chatMessages = listOf(
            ChatMessage(ChatRole.System, "안녕! 질문이 있어"),
            ChatMessage(ChatRole.User, request.question)
        )

        val response = openAI.chatCompletion(
            request = ChatCompletionRequest(
                model = request.model,
                messages = chatMessages,
                functions = params,
                functionCall = FunctionMode.Auto
            )
        )

        val message = response.choices.first().message
        val finishReason = response.choices.first().finishReason

        if ((finishReason?.value ?: throw RuntimeException("Fail call open ai")) in setOf("tool_calls", "function_call")) {

            val functionCall = message.functionCall
                ?: return LLMResponse("함수 호출 정보가 없습니다.")

            val functionName = functionCall.name
            val argumentsJson = Json.parseToJsonElement(functionCall.arguments).jsonObject
            val argsMap = argumentsJson.toStringMap()
            
            val answer = provideFunction(functionName, argsMap)
            return LLMResponse(answer)
        }

        throw RuntimeException("Error")
    }

    fun JsonObject.toStringMap(): Map<String, String> =
        this.mapValues { it.value.toString().trim('"') }
}