package `week1-refactoring`.domain.presentation

import `week1-refactoring`.domain.llm.dto.model.LLMRequest
import `week1-refactoring`.domain.llm.dto.model.LLMResponse


interface LLMRepository {
    suspend fun callOpenAi(request: LLMRequest): LLMResponse
}
