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
        // 파일을 읽어서 Properties 객체에 로드
        FileInputStream(envFile).use { input ->
            Properties().load(input)
        }
        // Properties에서 해당 key의 값을 찾으면 반환
        Properties().getProperty(key)?.let { return it }
    }

    // 환경 변수(System Environment)에서 해당 키를 찾을 경우 반환
    System.getenv(key)?.let { return it }

    // .env에도 없고 환경 변수에도 없을 경우 예외 발생
    throw FileNotFoundException("API key for '$key' not found in .env or environment variables.")
}