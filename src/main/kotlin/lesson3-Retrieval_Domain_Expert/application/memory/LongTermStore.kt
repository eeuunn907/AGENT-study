package `lesson3-Retrieval_Domain_Expert`.application.memory

import `lesson3-Retrieval_Domain_Expert`.application.agent.EmbeddingProvider
import `lesson3-Retrieval_Domain_Expert`.application.retrieval.Document
import `lesson3-Retrieval_Domain_Expert`.application.retrieval.VectorStore

class LongTermStore(val vectorStore: VectorStore, val embeddingProvider: EmbeddingProvider) {
    fun addDocument(doc: Document) {
        val vec = embeddingProvider.embed(doc.id)
        vectorStore.upsert(doc.id, vec, doc)
    }

    fun retrieveByQuery(query: String, topK: Int = 5): List<Pair<Document, Float>> {
        val vec = embeddingProvider.embed(query)
        return vectorStore.similaritySearch(vec, topK)
    }
}
