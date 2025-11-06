package lesson1.presentation

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import lesson1.data.OpenAiRepositoryImpl
import lesson1.domain.usecase.CallOpenAiUseCase

/* Open API를 호출해서 여러 테스트 문장을 동시에 실행하고 결과를 출력하는 ViewModel */
class WeekOneViewModel {
    // 테스트 문장
    private val testSets = listOf(
        "파이썬 디자인 패턴에 관심있는데 읽을 만한 블로그 링크 줘.",
        "안다르의 “울라이크 스트레치 와이드 슬랙스” 상품 가격이 얼마야?",
        "토스의 프로덕트 ADCIO, Agent Village에 대해 각각 설명해줘."
    )

    // OpenAI API 호출 함 / runBlocking는 비동기로 실행하도록 함
    fun callOpenAi() = runBlocking {
        // testSets 문장 만큼 반복
        testSets.forEach { testSet ->
            launch {
                val result = CallOpenAiUseCase(
                    openAiRepository = OpenAiRepositoryImpl()
                ).execute(
                    model = "gpt-4",
                    content = testSet,
                )
                // 질문
                println("Question: \n$testSet\n")
                println("====================================")

                // 응답
                println("Answer: \n$result")
                println("====================================")
            }
        }
    }
}