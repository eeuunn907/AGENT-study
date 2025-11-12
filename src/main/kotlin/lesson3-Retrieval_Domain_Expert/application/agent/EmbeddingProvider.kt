package `lesson3-Retrieval_Domain_Expert`.application.agent

interface EmbeddingProvider {
    fun embed(text: String): FloatArray
    val dimension: Int
}
