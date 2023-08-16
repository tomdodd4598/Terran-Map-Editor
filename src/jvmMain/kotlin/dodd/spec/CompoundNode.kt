package dodd.spec

import dodd.ListHashMap
import kotlin.math.max

abstract class CompoundNode : Node() {

    val internal = ListHashMap<String, Node>()

    inline operator fun <reified T: Node> get(key: String): T? {
        val value = internal[key]
        return if (value is T) value else null
    }

    open operator fun set(key: String, value: Node) {
        internal[key] = value
    }

    override fun toString() = "${javaClass.simpleName}$internal"
}

class TupleNode(vararg pairs: Pair<String, Node>) : CompoundNode() {

    init {
        for ((name, node) in pairs) {
            this[name] = node
        }
    }

    override fun write(sb: SpecBuilder, name: String) {
        for ((key, value) in internal) {
            value.write(sb, key)
        }
    }
}

open class NestedNode : CompoundNode(), StackElement {

    private var rawPrevious: RawNode? = null

    open fun innerKey(key: String) = key

    override fun write(sb: SpecBuilder, name: String) {
        val inner = sb.inner()
        for ((key, value) in internal) {
            value.write(inner, innerKey(key))
        }
        val lines = inner.lines()

        val size = max(0, lines.size - 1)
        sb.append("${size.toString().padStart(8, '0')} $name")
        sb.append("{")
        lines.asSequence().take(size).forEach { sb.append(it) }
        sb.append("}")
    }

    override fun set(key: String, value: Node) {
        super.set(key, value)
        rawPrevious = if (value is RawNode) value else null
    }

    override fun rawPrevious() = rawPrevious

    override fun tryNext(lineIter: Iterator<String>, start: Boolean) = Pair(lineIter.next(), true)
}

class RootNode : NestedNode() {

    override fun write(sb: SpecBuilder, name: String) {
        for ((key, value) in internal) {
            value.write(sb, key)
        }
    }

    fun buildSpec(): SpecBuilder {
        val sb = SpecBuilder(false)
        write(sb, "root")
        return sb
    }
}

class ConditionNode : NestedNode()

class ActionNode : NestedNode()

class MixNode : NestedNode() {

    private var index = 0

    override fun set(key: String, value: Node) {
        super.set("$index $key", value)
        ++index
    }

    override fun innerKey(key: String) = key.split(" ", limit = 2)[1]
}
