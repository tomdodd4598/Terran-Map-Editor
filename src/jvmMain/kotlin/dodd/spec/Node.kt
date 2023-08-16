package dodd.spec

abstract class Node {

    abstract fun write(sb: SpecBuilder, name: String)

    abstract override fun toString(): String
}

class RawNode(private val line: String) : Node() {

    override fun write(sb: SpecBuilder, name: String) {
        sb.append(line)
    }

    override fun toString() = line
}
