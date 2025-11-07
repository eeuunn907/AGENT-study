package `lesson1-LLM_API_Web_Surfer`.domain.usecase

import com.aallam.openai.api.model.ModelId
import `lesson1-LLM_API_Web_Surfer`.domain.dto.request.OpenAiRequest
import `lesson1-LLM_API_Web_Surfer`.domain.repository.OpenAiRepository

class CallOpenAiUseCase(
    private val openAiRepository: OpenAiRepository
) {
    suspend fun execute(model: String, content: String): String {
        val response = openAiRepository.callOpenAi(
            request = OpenAiRequest(
                model = ModelId(model),
                question = content
            )
        )
        return response.answer
    }
}