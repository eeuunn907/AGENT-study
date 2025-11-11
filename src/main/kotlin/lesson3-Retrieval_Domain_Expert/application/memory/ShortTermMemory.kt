package `lesson3-Retrieval_Domain_Expert`.application.memory

class ShortTermMemory(val capacity: Int = 64) {
    private val map = object : LinkedHashMap<String, String>(16, 0.75f, true) {
        override fun removeEldestEntry(eldest: MutableMap.MutableEntry<String, String>?): Boolean {
            return size > capacity
        }
    }

    @Synchronized
    fun put(key: String, value: String) {
        map[key] = value
    }

    @Synchronized
    fun get(key: String): String? = map[key]

    @Synchronized
    fun all(): List<Pair<String, String>> = map.entries.map { Pair(it.key, it.value) }
}