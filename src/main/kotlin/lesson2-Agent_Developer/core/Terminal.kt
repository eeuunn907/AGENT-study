package `lesson2-Agent_Developer`.core

import java.io.BufferedReader

object Terminal {
    fun runCommand(command: String): String {
        val process = ProcessBuilder(*command.split(" ").toTypedArray())
            .redirectErrorStream(true)
            .start()

        val output = process.inputStream.bufferedReader().use(BufferedReader::readText)
        process.waitFor()
        return output
    }
}
