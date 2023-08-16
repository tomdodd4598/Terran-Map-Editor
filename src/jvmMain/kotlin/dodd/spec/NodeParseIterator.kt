package dodd.spec

class NodeParseIterator(private val internal: Iterator<String>) {

    fun hasNext() = internal.hasNext()

    fun tryNext(element: StackElement, start: Boolean) = element.tryNext(internal, start)
}
