package `lesson1-refactoring`.domain.presentation

import `lesson1-refactoring`.domain.llm.dto.model.LLMRequest
import `lesson1-refactoring`.domain.llm.dto.model.LLMResponse


interface LLMRepository {
    suspend fun callOpenAi(request: LLMRequest): LLMResponse
}
