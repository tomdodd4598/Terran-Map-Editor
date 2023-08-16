package dodd

import dodd.Helpers.child
import dodd.Helpers.splitByWhitespace
import java.io.File

class Defs(gameDir: File, name: String) : Iterable<String> {

    private val internal = mutableListOf<String>()

    init {
        val file = gameDir.child("Strings/GameDefines/${name}_GameStrings.h")

        for (line in file.readLines()) {
            val trim = line.trim()
            if (trim.isNotEmpty() && !trim.startsWith("//")) {
                internal.add(trim.splitByWhitespace()[1])
            }
        }
    }

    override fun iterator() = internal.iterator()
}
