package `lesson3-Retrieval_Domain_Expert`.application.agent

import `lesson3-Retrieval_Domain_Expert`.application.memory.LongTermStore
import `lesson3-Retrieval_Domain_Expert`.application.memory.ShortTermMemory
import `lesson3-Retrieval_Domain_Expert`.application.retrieval.Document

interface Executor {
    fun execute(plans: List<String>, maxHops: Int = 3, topK: Int = 5): String
}
class EvidenceAggregator(val longTermStore: LongTermStore, val shortTermMemory: ShortTermMemory) : Executor {

    override fun execute(plans: List<String>, maxHops: Int, topK: Int): String {
        val evidences = ArrayList<String>()

        for ((i, plan) in plans.withIndex()) {
            shortTermMemory.put("plan-${i}", plan)

            var currentQuery = plan
            val visitedDocs = HashSet<String>()
            for (hop in 0 until maxHops) {
                val results = longTermStore.retrieveByQuery(currentQuery, topK)
                if (results.isEmpty()) break

                var appendedAny = false
                for ((doc, score) in results) {
                    if (visitedDocs.contains(doc.id)) continue
                    visitedDocs.add(doc.id)
                    evidences.add(buildEvidenceSnippet(doc, score, hop, plan))
                    appendedAny = true
                }

                if (!appendedAny) break

                val topTitles = results.take(2).joinToString("; ") { it.first.title }
                currentQuery = "$plan; see: $topTitles"
            }
        }

        val sb = StringBuilder()
        sb.append("Answer synthesis:\n")
        for ((idx, plan) in plans.withIndex()) {
            sb.append("\n[Plan ${idx+1}] ${plan}\n")
            val related = evidences.filter { it.contains("planId=$idx;") }
            if (related.isEmpty()) {
                sb.append("  - (no evidence found)\n")
            } else {
                for (e in related) sb.append("  - $e\n")
            }
        }

        sb.append("\nSummary:\n")
        val summary = createSummary(evidences)
        sb.append(summary)

        return sb.toString()
    }

    private fun buildEvidenceSnippet(doc: Document, score: Float, hop: Int, plan: String): String {
        val planId = shortTermMemory.all().indexOfFirst { it.second == plan }
        val snippet = doc.text.split(Regex("(?<=\\.)")) // split to sentences
            .firstOrNull()?.trim()?.take(300) ?: doc.text.take(300)
        return "planId=$planId; hop=$hop; score=${"%.3f".format(score)}; doc=${doc.title}; snippet=${snippet.replace('\n', ' ')}"
    }

    private fun createSummary(evidences: List<String>, maxSentences: Int = 6): String {
        if (evidences.isEmpty()) return "No evidence to summarize."
        val byScore = evidences.mapNotNull { ev ->
            val m = Regex("score=([0-9.]+)").find(ev)
            val score = m?.groupValues?.get(1)?.toFloatOrNull() ?: 0f
            Pair(ev, score)
        }.sortedByDescending { it.second }
        return byScore.take(maxSentences).joinToString("\n") { it.first }
    }
}