package `lesson1-LLM_API_Web_Surfer`.domain.repository

import `lesson1-LLM_API_Web_Surfer`.domain.dto.request.OpenAiRequest
import `lesson1-LLM_API_Web_Surfer`.domain.dto.response.OpenAiResponse

interface OpenAiRepository {
    suspend fun callOpenAi(request: OpenAiRequest): OpenAiResponse
}