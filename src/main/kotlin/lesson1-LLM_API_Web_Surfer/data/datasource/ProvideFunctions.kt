// 특정 플러그인 관련 경고를 무시하기 위한 어노테이션
@file:Suppress("PLUGIN_IS_NOT_ENABLED")

package `lesson1-LLM_API_Web_Surfer`.data.datasource

import `lesson1-LLM_API_Web_Surfer`.data.datasource.provide.getLink
import `lesson1-LLM_API_Web_Surfer`.data.datasource.provide.productPrice
import `lesson1-LLM_API_Web_Surfer`.data.datasource.provide.serviceDescription

fun availableFunctions(functionName: String, value: Map<String, String>): String {
    // 전달받은 functionName 값에 따라 실행할 함수를 분기 처리
    return when (functionName) {
        // "get_link" 요청 시 → Google 검색 결과 링크 리스트 반환
        "get_link" -> getLink(value)

        // "product_price" 요청 시 → 특정 상점의 상품 가격 정보를 반환
        "product_price" -> productPrice(value)

        // "service_description" 요청 시 → 회사의 서비스 소개를 OpenAI를 통해 생성하여 반환
        "service_description" -> serviceDescription(value)

        // 정의되지 않은 함수명이 들어오면 예외 발생
        else -> throw RuntimeException("function name is incorrect")
    }
}