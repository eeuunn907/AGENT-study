package week1.domain.dto.request

import com.aallam.openai.api.model.ModelId

class OpenAiRequest(
    val model: ModelId,
    val question: String
)