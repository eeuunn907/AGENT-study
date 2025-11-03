import kotlinx.coroutines.runBlocking
import `week1-refactoring`.domain.llm.service.LLMService

fun main() = runBlocking {
    try {
        val vm = LLMService()
        vm.callOpenAi()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

//fun main() {
//    println(
//        """
//        ============================
//          🤖 Developer Agent Started
//        ============================
//
//        📜 예시 요청:
//          1. Please develop and serve a webpage that displays hello world.
//          2. Please develop and serve a webpage that allows me to move a box with my mouse.
//          3. Develop and serve a webpage that shows the total number of page views.
//          4. Please develop and serve a simple todo webpage. The user should be able to add and delete todo and see all todos. Develop in a neumorphism style.
//
//        💬 Enter your request:
//        """.trimIndent()
//    )
//
//    val request = readln()
//    DeveloperAgent.handleRequest(request)
//}