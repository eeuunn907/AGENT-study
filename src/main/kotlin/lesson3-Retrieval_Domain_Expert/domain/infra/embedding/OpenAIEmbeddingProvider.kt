package `lesson3-Retrieval_Domain_Expert`.domain.infra.embedding

import `lesson3-Retrieval_Domain_Expert`.application.agent.EmbeddingProvider

class OpenAIEmbeddingProvider(override val dimension: Int = 1536, val apiKey: String) : EmbeddingProvider {
    override fun embed(text: String): FloatArray {
        throw UnsupportedOperationException("Implement HTTP call to OpenAI embeddings endpoint here")
    }
}
