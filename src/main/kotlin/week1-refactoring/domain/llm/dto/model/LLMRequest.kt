package `week1-refactoring`.domain.llm.dto.model

import com.aallam.openai.api.model.ModelId

class LLMRequest(
    val model: ModelId,
    val question: String
)
