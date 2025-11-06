package lesson1.data.datasource.provide

import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import kotlinx.coroutines.runBlocking
import lesson1.data.datasource.provide.google.requestGoogle
import lesson1.domain.key.openAiKey

fun serviceDescription(args: Map<String, String>): String {
    // args에서 회사명과 서비스명을 가져옴 (없으면 예외 발생)
    val companyName = args["companyName"] ?: throw IllegalArgumentException("Company name is required.")
    val serviceName = args["serviceNames"] ?: throw IllegalArgumentException("Service name is required.")

    // Google Custom Search API를 이용해 "회사명 + 서비스명 + 서비스 소개"로 검색 수행
    val information = requestGoogle("$companyName 의 $serviceName 서비스 소개")

    // OpenAI API 호출 -> 비동기
    val response = runBlocking {
        callOpenAi(companyName, serviceName, information)
    }

    return response
}

// OpenAI API를 호출하여 GPT 모델에게 서비스 설명을 요청하는 함수
private suspend fun callOpenAi(companyName: String, serviceName: String, information: String): String {
    val openAI = OpenAI(
        token = openAiKey
    )

    // 대화 메시지 리스트 구성
    // 첫 번째 메시지는 System 역할로 GPT에게 요청 맥락을 전달
    // 두 번째 메시지는 User 역할로 Google 검색 결과 정보를 제공
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

    // GPT-4 모델을 이용해 채팅 요청 수행
    val response = openAI.chatCompletion(
        request = ChatCompletionRequest(
            model = ModelId("gpt-4"), // 사용할 모델 지정
            messages = chatMessages, // 대화 메시지 전달
        )
    )

    // GPT의 첫 번째 응답 메시지 내용 반환
    return response.choices.first().message.content!!
}