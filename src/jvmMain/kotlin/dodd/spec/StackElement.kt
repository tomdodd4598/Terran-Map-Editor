package dodd.spec

interface StackElement {

    operator fun set(key: String, value: Node)

    fun rawPrevious(): RawNode?

    fun tryNext(lineIter: Iterator<String>, start: Boolean): Pair<String, Boolean>
}
