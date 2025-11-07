package `lesson1-LLM_API_Web_Surfer`.domain.dto.request

import com.aallam.openai.api.model.ModelId

class OpenAiRequest(
    val model: ModelId,
    val question: String
)