package `week1-refactoring`.domain.search.model

import `week1-refactoring`.domain.agent.link
import `week1-refactoring`.domain.agent.productPrice
import `week1-refactoring`.domain.agent.serviceDescription


fun provideFunction(functionName: String, value: Map<String, String>): String {
    return when (functionName) {
        "get_link" -> link(value)

        "product_price" -> productPrice(value)

        "service_description" -> serviceDescription(value)

        else -> throw RuntimeException("function name is incorrect")
    }
}
