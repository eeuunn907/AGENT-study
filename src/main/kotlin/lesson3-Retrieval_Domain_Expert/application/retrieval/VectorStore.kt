package `lesson3-Retrieval_Domain_Expert`.application.retrieval

import kotlin.math.sqrt

interface VectorStore {
    fun upsert(id: String, vector: FloatArray, doc: Document)
    fun similaritySearch(queryVector: FloatArray, topK: Int): List<Pair<Document, Float>>
}

class InMemoryVectorStore : VectorStore {
    private val vectors = HashMap<String, FloatArray>()
    private val docs = HashMap<String, Document>()

    override fun upsert(id: String, vector: FloatArray, doc: Document) {
        vectors[id] = vector
        docs[id] = doc
    }

    override fun similaritySearch(queryVector: FloatArray, topK: Int): List<Pair<Document, Float>> {
        val scores = ArrayList<Pair<Document, Float>>()
        for ((id, vec) in vectors) {
            val sim = cosineSimilarity(queryVector, vec)
            docs[id]?.let { scores.add(Pair(it, sim)) }
        }
        return scores.sortedByDescending { it.second }.take(topK)
    }

    private fun cosineSimilarity(a: FloatArray, b: FloatArray): Float {
        var dot = 0.0f
        var na = 0.0f
        var nb = 0.0f
        for (i in a.indices) {
            dot += a[i] * b[i]
            na += a[i] * a[i]
            nb += b[i] * b[i]
        }
        val denom = sqrt(na) * sqrt(nb)
        return if (denom == 0.0f) 0.0f else dot / denom
    }
}