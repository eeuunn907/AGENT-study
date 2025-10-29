import kotlinx.coroutines.runBlocking
import week1.presentation.WeekOneViewModel

fun main() = runBlocking {
    try {
        val vm = WeekOneViewModel()
        vm.callOpenAi()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}