package week1.data

import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.client.OpenAI
import params
import week1.data.datasource.availableFunctions
import week1.domain.dto.request.OpenAiRequest
import week1.domain.dto.response.OpenAiResponse
import week1.domain.key.openAiKey
import week1.domain.repository.OpenAiRepository
import week1.domain.util.parseJsonToMap

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

        if ((finishReason?.value ?: throw RuntimeException("Fail call open ai")) == "function_call") {
            val functionType = message.functionCall?.nameOrNull!!
            message.functionCall?.let { functionCall ->
                val functionArgs = functionCall.argumentsAsJson().parseJsonToMap()

                println(functionArgs)
                val answer = availableFunctions(functionType, functionArgs)
                return OpenAiResponse(answer)
            } ?: println(message.content)
        }

        throw RuntimeException("Error")
    }
}