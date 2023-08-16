package dodd.spec

class SpecBuilder(private val indent: Boolean) {

    private val internal = StringBuilder()

    fun inner() = SpecBuilder(true)

    fun append(line: String) {
        if (indent) {
            internal.append("\t")
        }
        internal.append(line)
        internal.append("\n")
    }

    fun lines() = internal.lines()

    override fun toString() = internal.toString()
}
