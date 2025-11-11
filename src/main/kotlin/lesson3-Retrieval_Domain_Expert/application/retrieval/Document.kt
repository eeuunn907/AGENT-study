package `lesson3-Retrieval_Domain_Expert`.application.retrieval

data class Document(
    val id: String,
    val title: String,
    val text: String,
    val metadata: Map<String, String> = emptyMap()
)