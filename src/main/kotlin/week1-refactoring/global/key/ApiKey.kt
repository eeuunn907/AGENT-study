package `week1-refactoring`.global.key

import java.io.FileNotFoundException
import java.util.*

val openAiKey: String = key("OPENAI_API_KEY")

val googleApiKey: String = key("GOOGLE_API_KEY")

val googleEngineKey: String = key("GOOGLE_SEARCH_ENGINE_ID")

fun key(key: String): String {
    val properties = Properties()
    val inputStream = Thread.currentThread().contextClassLoader.getResourceAsStream("local.properties")
        ?: throw FileNotFoundException("Property file 'local.properties' not found in the classpath")
    properties.load(inputStream)
    return properties.getProperty(key) ?: throw IllegalArgumentException("API Key not found")
}
