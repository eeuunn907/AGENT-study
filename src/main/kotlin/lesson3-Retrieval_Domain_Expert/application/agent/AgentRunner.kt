package `lesson3-Retrieval_Domain_Expert`.application.agent

import `lesson3-Retrieval_Domain_Expert`.application.memory.LongTermStore
import `lesson3-Retrieval_Domain_Expert`.application.memory.ShortTermMemory
import `lesson3-Retrieval_Domain_Expert`.application.retrieval.Document
import `lesson3-Retrieval_Domain_Expert`.application.retrieval.InMemoryVectorStore
import `lesson3-Retrieval_Domain_Expert`.domain.infra.embedding.DummyEmbeddingProvider

object AgentRunner {
    @JvmStatic
    fun main(args: Array<String>) {
        val embedding = DummyEmbeddingProvider(64)
        val vectorStore = InMemoryVectorStore()
        val longTerm = LongTermStore(vectorStore, embedding)
        val shortTerm = ShortTermMemory(128)

        seedDocs(longTerm)

        val planner = HeuristicPlanner()
        val executor = EvidenceAggregator(longTerm, shortTerm)
        val agent = RetrievalAgent(planner, executor)

        val tests = listOf(
            "플래닝과 리액팅을 어떻게 해야 하는지 설명해줘.",
            "MIPS, ChemCrow, Locality-Sensitive Hashing 각각이 뭐야?",
            "에이전트가 문제를 풀기 위해서 쪼개고 효과적으로 핸들링하는 과정을 20줄 이하로 임팩트있게 요약해줘."
        )

        for ((i, t) in tests.withIndex()) {
            println("\\n===== Test ${i+1} =====")
            val out = agent.ask(t)
            println(out)
            println("======================\\n")
        }
    }

    private fun seedDocs(longTerm: LongTermStore) {
        val docs = listOf(
            Document("d1", "Agent Overview", "An agent is a system that perceives its environment and takes actions to achieve goals. The agent loop often has planning, acting, observing and learning components."),
            Document("d2", "Planner vs Reactor", "Planning decomposes a task into sub-goals before acting. Reacting performs short-term loop: observe, decide, act, and can be interleaved with planning. The hybrid approach (plan+react) helps in dynamic environments."),
            Document("d3", "MIPS and modular frameworks", "MIPS is a modular system for planning. It designs modules for perception, planning, and execution. The article surveys MIPS as one example among others like ChemCrow.") ,
            Document("d4", "ChemCrow", "ChemCrow is an agentic system specialized for chemistry tasks, combining tools and LLMs to perform lab workflows safely. It composes chain-of-tools for complex tasks.") ,
            Document("d5", "Locality-Sensitive Hashing", "LSH is an approximate nearest neighbor algorithm that hashes vectors so that similar vectors collide with high probability. It speeds up retrieval by avoiding linear scans.") ,
            Document("d6", "Multi-hop retrieval", "Multi-hop retrieval performs several retrieval rounds: use initial query to retrieve docs, expand query with top-docs, retrieve again. Useful for complex questions requiring evidence from multiple sources.")
        )
        for (d in docs) longTerm.addDocument(d)
    }
}