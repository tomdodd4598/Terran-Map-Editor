package dodd

import dodd.Helpers.child
import java.io.File

class Locale(gameDir: File, lang: String) {

    private val internal = mutableMapOf<String, String>()

    init {
        val file = gameDir.child("Strings/${lang}_GameStrings.txt")
        val lineIter = file.readLines().iterator()

        fun nextLine() = lineIter.next().trim()

        while (lineIter.hasNext()) {
            val line = nextLine()
            if (line.isNotEmpty() && !line.startsWith("//")) {
                internal[line] = nextLine().removeSurrounding("\"", "\"")
            }
        }
    }

    operator fun get(key: String) = internal[key]!!
}
