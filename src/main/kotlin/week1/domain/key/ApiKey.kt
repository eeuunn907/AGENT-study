package week1.domain.key

import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.util.Properties


val openAiKey: String = loadApiKey("OPENAI_API_KEY")

val googleApiKey: String = loadApiKey("GOOGLE_API_KEY")

val googleEngineKey: String = loadApiKey("GOOGLE_SEARCH_ENGINE_ID")

fun loadApiKey(key: String): String {
    val envFile = File(".env")

    if (envFile.exists()) {
        FileInputStream(envFile).use { input ->
            Properties().load(input)
        }
        Properties().getProperty(key)?.let { return it }
    }

    System.getenv(key)?.let { return it }

    throw FileNotFoundException("API key for '$key' not found in .env or environment variables.")
}