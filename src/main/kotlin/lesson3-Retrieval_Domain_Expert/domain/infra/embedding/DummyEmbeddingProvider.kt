package `lesson3-Retrieval_Domain_Expert`.domain.infra.embedding

import `lesson3-Retrieval_Domain_Expert`.application.agent.EmbeddingProvider
import kotlin.math.sqrt
import kotlin.random.Random

class DummyEmbeddingProvider(override val dimension: Int = 64) : EmbeddingProvider {
    private val rnd = Random(1234)
    override fun embed(text: String): FloatArray {
        val hash = text.hashCode()
        val vec = FloatArray(dimension)
        var a = hash
        for (i in 0 until dimension) {
            a = a * 1664525 + 1013904223
            vec[i] = ((a shr (i % 16)) and 0xFF).toFloat() / 255f
        }
        var norm = 0.0f
        for (v in vec) norm += v * v
        norm = sqrt(norm)
        if (norm == 0f) return vec
        for (i in vec.indices) vec[i] = vec[i] / norm
        return vec
    }
}
