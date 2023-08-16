package dodd

import dodd.spec.SpecParser
import java.io.File
import kotlin.collections.ArrayDeque

object Helpers {
    fun <T, V> T.letIfNotNull(value: V?, block: (V) -> T) = if (value == null) this else block(value)

    fun <T> Iterable<T>.toMutableMap(): MutableMap<Int, T> {
        val map = mutableMapOf<Int, T>()
        forEachIndexed { index, elem -> map[index] = elem }
        return map
    }

    fun <T> ArrayDeque<T>.push(elem: T) = addLast(elem)

    fun <T> ArrayDeque<T>.pop() = removeLast()

    fun <T> ArrayDeque<T>.peek() = last()

    fun Sequence<String>.filterNotEmpty() = filter { it.isNotEmpty() }

    fun Sequence<Float>.specStr(type: String) = joinToString(", ", "$type( ", " )") { it.specStr() }

    fun File.child(path: String) = File("$absolutePath/$path")

    fun String.splitByWhitespace() = split(Global.whitespaceRegex)

    private fun Float.places(n: Int) = "%.${n}f".format(this)

    fun Float.specStr() = places(6)

    private fun Double.places(n: Int) = "%.${n}f".format(this)

    fun Double.specStr() = places(6)

    fun File.parseSpec() = SpecParser(this).result()
}
