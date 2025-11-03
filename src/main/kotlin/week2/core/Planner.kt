package week2.core

data class Plan(val goal: String, val steps: List<String>)

object Planner {
    fun createPlan(request: String): Plan {
        val req = request.lowercase()
        return when {
            "hello world" in req -> Plan(
                "Display a Hello World page",
                listOf("Create index.html", "Serve webpage")
            )
            "move a box" in req -> Plan(
                "Draggable box webpage",
                listOf("Create HTML+JS for draggable box", "Serve webpage")
            )
            "page views" in req -> Plan(
                "Page view counter webpage",
                listOf("Store counter", "Serve updated count each request")
            )
            "todo" in req -> Plan(
                "Neumorphism todo app",
                listOf("Add/delete todos", "Style with CSS", "Serve webpage")
            )
            else -> Plan("Unknown request", listOf())
        }
    }
}
