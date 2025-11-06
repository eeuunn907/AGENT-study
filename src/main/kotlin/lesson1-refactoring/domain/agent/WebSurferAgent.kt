package `lesson1-refactoring`.domain.agent

import com.aallam.openai.api.model.ModelId
import `lesson1-refactoring`.domain.llm.dto.model.LLMRequest
import `lesson1-refactoring`.domain.presentation.LLMRepository

class WebSurferAgent(
    private val openAiRepository: LLMRepository
) {
    suspend fun execute(model: String, content: String): String {
        val response = openAiRepository.callOpenAi(
            request = LLMRequest(
                model = ModelId(model),
                question = content
            )
        )
        return response.answer
    }
}
