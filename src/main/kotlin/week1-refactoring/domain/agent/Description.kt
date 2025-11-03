package `week1-refactoring`.domain.agent

import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import kotlinx.coroutines.runBlocking
import `week1-refactoring`.domain.llm.dto.googleRequest
import `week1-refactoring`.global.key.openAiKey

fun serviceDescription(args: Map<String, String>): String {
    val companyName = args["companyName"] ?: throw IllegalArgumentException("Company name is required.")
    val serviceName = args["serviceNames"] ?: throw IllegalArgumentException("Service name is required.")

    val information = googleRequest("$companyName 의 $serviceName 서비스 소개")

    val response = runBlocking {
        callOpenAi(companyName, serviceName, information)
    }

    return response
}

private suspend fun callOpenAi(companyName: String, serviceName: String, information: String): String {
    val openAI = OpenAI(
        token = openAiKey
    )

    val chatMessages = mutableListOf(
        ChatMessage(
            role = ChatRole.System,
            content = "안녕 GPT야. $companyName 의 $serviceName 서비스(들)에 대해서 자세하게 설명해주겠니?"
        ),
        ChatMessage(
            role = ChatRole.User,
            content = information
        )
    )

    val response = openAI.chatCompletion(
        request = ChatCompletionRequest(
            model = ModelId("gpt-4"),
            messages = chatMessages,
        )
    )

    return response.choices.first().message.content!!
}
