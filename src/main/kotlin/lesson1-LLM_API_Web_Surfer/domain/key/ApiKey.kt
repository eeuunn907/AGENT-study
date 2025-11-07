package `lesson1-LLM_API_Web_Surfer`.domain.key

import java.io.FileNotFoundException
import java.util.Properties

val openAiKey: String = loadApiKey("OPENAI_API_KEY")

val googleApiKey: String = loadApiKey("GOOGLE_API_KEY")

val googleEngineKey: String = loadApiKey("GOOGLE_SEARCH_ENGINE_ID")

fun loadApiKey(key: String): String {
    val properties = Properties()
    val inputStream = Thread.currentThread().contextClassLoader.getResourceAsStream("local.properties")
        ?: throw FileNotFoundException("Property file 'local.properties' not found in the classpath")
    properties.load(inputStream)
    return properties.getProperty(key) ?: throw IllegalArgumentException("API Key not found")
}