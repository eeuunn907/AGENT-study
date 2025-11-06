package lesson1.domain.usecase

import com.aallam.openai.api.model.ModelId
import lesson1.domain.dto.request.OpenAiRequest
import lesson1.domain.repository.OpenAiRepository

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