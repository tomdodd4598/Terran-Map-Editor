package dodd

class Matrix<T>(private val internal: Array<T>, private val rowSize: Int) {

    operator fun get(rowIndex: Int, colIndex: Int) = internal[rowSize * rowIndex + colIndex]

    operator fun set(rowIndex: Int, colIndex: Int, value: T) {
        internal[rowSize * rowIndex + colIndex] = value
    }

    operator fun iterator() = internal.iterator()

    fun asSequence() = internal.asSequence()

    override fun toString() = internal.contentDeepToString()
}

class FloatMatrix(private val internal: FloatArray, private val rowSize: Int) {

    operator fun get(rowIndex: Int, colIndex: Int) = internal[rowSize * rowIndex + colIndex]

    operator fun set(rowIndex: Int, colIndex: Int, value: Float) {
        internal[rowSize * rowIndex + colIndex] = value
    }

    operator fun iterator() = internal.iterator()

    fun asSequence() = internal.asSequence()

    override fun toString() = internal.contentToString()
}

class DoubleMatrix(private val internal: DoubleArray, private val rowSize: Int) {

    operator fun get(rowIndex: Int, colIndex: Int) = internal[rowSize * rowIndex + colIndex]

    operator fun set(rowIndex: Int, colIndex: Int, value: Double) {
        internal[rowSize * rowIndex + colIndex] = value
    }

    operator fun iterator() = internal.iterator()

    fun asSequence() = internal.asSequence()

    override fun toString() = internal.contentToString()
}
