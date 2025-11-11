package `lesson3-Retrieval_Domain_Expert`.application.agent

interface Planner {
    fun plan(question: String): List<String>
}

class HeuristicPlanner : Planner {
    override fun plan(question: String): List<String> {
        val q = question.trim()
        if (q.length < 80 && !q.contains(",") && !q.contains(" and ", ignoreCase = true)) {
            return listOf(q)
        }
        val parts = q.split(Regex("[,;\\.\\?]")).map { it.trim() }.filter { it.isNotEmpty() }
        if (parts.size <= 1) {
            return q.split(Regex("\\band\\b", RegexOption.IGNORE_CASE)).map { it.trim() }.filter { it.isNotEmpty() }
        }
        return parts
    }
}
