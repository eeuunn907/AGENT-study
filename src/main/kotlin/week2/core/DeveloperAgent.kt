package week2.core

import week2.tool.WebDevTool

object DeveloperAgent {

    fun handleRequest(request: String) {
        val plan = Planner.createPlan(request)
        println("🧠 Plan: ${plan.goal}")
        println("🪜 Steps: ${plan.steps.joinToString()}")

        val req = request.lowercase()
        when {
            "hello world" in req -> WebDevTool.createHelloWorldPage()
            "move a box" in req -> WebDevTool.createDraggableBoxPage()
            "page views" in req -> WebDevTool.createPageViewPage()
            "todo" in req -> WebDevTool.createTodoPage()
            else -> {
                println("Unknown request")
                return
            }
        }

        WebDevTool.serveWebPage(request)
    }
}
