package week1.domain.repository

import week1.domain.dto.request.OpenAiRequest
import week1.domain.dto.response.OpenAiResponse

interface OpenAiRepository {
    suspend fun callOpenAi(request: OpenAiRequest): OpenAiResponse
}