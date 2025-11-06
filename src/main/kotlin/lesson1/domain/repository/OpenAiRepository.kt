package lesson1.domain.repository

import lesson1.domain.dto.request.OpenAiRequest
import lesson1.domain.dto.response.OpenAiResponse

interface OpenAiRepository {
    suspend fun callOpenAi(request: OpenAiRequest): OpenAiResponse
}