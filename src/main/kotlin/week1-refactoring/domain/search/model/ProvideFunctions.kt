package `week1-refactoring`.domain.search.model

import `week1-refactoring`.domain.agent.link
import `week1-refactoring`.domain.agent.productPrice
import `week1-refactoring`.domain.agent.serviceDescription


fun provideFunction(functionName: String, value: Map<String, String>): String {
    return when (functionName) {
        "link" -> link(value)

        "price" -> productPrice(value)

        "description" -> serviceDescription(value)

        else -> throw RuntimeException("function name is incorrect")
    }
}
