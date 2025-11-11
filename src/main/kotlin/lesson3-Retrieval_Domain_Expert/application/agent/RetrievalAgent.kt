package `lesson3-Retrieval_Domain_Expert`.application.agent

class RetrievalAgent(
    private val planner: Planner,
    private val executor: Executor
) {
    fun ask(question: String): String {
        val plans = planner.plan(question)
        return executor.execute(plans)
    }
}