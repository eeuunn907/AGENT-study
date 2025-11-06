package `lesson1-refactoring`.domain.llm.service

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import `lesson1-refactoring`.domain.agent.OpenAI
import `lesson1-refactoring`.domain.agent.WebSurferAgent

class LLMService {
    private val testSets = listOf(
        "파이썬 디자인 패턴에 관심있는데 읽을 만한 블로그 링크 줘.",
        "안다르의 “울라이크 스트레치 와이드 슬랙스” 상품 가격이 얼마야?",
        "코르카의 프로덕트 ADCIO, Agent Village에 대해 각각 설명해줘."
    )

    fun callOpenAi() = runBlocking {
        testSets.forEach { testSet ->
            launch {
                val result = WebSurferAgent(
                    openAiRepository = OpenAI()
                ).execute(
                    model = "gpt-4.1-mini",
                    content = testSet,
                )
                println("Question: \n$testSet\n")
                println("====================================")

                println("Answer: \n$result")
                println("====================================")
            }
        }
    }
}
