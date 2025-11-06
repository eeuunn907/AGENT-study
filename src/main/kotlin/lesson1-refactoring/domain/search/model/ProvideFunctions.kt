package `lesson1-refactoring`.domain.search.model

import `lesson1-refactoring`.domain.agent.link
import `lesson1-refactoring`.domain.agent.productPrice
import `lesson1-refactoring`.domain.agent.serviceDescription


fun provideFunction(functionName: String, value: Map<String, String>): String {
    return when (functionName) {
        "link" -> link(value)

        "price" -> productPrice(value)

        "description" -> serviceDescription(value)

        else -> throw RuntimeException("function name is incorrect")
    }
}
