package `week1-refactoring`.domain.presentation

import `week1-refactoring`.domain.llm.model.LLMRequest
import `week1-refactoring`.domain.llm.model.LLMResponse


interface LLMRepository {
    suspend fun callOpenAi(request: LLMRequest): LLMResponse
}
