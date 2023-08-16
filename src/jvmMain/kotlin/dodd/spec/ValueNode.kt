package dodd.spec

import dodd.FloatMatrix
import dodd.Helpers.specStr

class StringNode(val value: String) : Node() {

    override fun write(sb: SpecBuilder, name: String) {
        sb.append("$name String '$value'")
    }

    override fun toString() = value
}

class BoolNode(val value: Boolean) : Node() {

    override fun write(sb: SpecBuilder, name: String) {
        sb.append("$name Bool ${if (value) "True" else "False"}")
    }

    override fun toString() = value.toString()
}

class IntNode(val value: Int) : Node() {

    override fun write(sb: SpecBuilder, name: String) {
        sb.append("$name Int $value")
    }

    override fun toString() = value.toString()
}

class FloatNode(val value: Float) : Node() {

    override fun write(sb: SpecBuilder, name: String) {
        sb.append("$name Float ${value.specStr()}")
    }

    override fun toString() = value.toString()
}

class DoubleNode(val value: Double) : Node() {

    override fun write(sb: SpecBuilder, name: String) {
        sb.append("$name Double ${value.specStr()}")
    }

    override fun toString() = value.toString()
}

class CoordNode(val value: FloatArray) : Node() {

    override fun write(sb: SpecBuilder, name: String) {
        sb.append("$name ${value.asSequence().specStr("Coord")}")
    }

    override fun toString() = "Coord${value.contentToString()}"
}

class VectorNode(val value: FloatArray) : Node() {

    override fun write(sb: SpecBuilder, name: String) {
        sb.append("$name ${value.asSequence().specStr("Vector3")}")
    }

    override fun toString() = "Vector${value.contentToString()}"
}

class ColorNode(val value: FloatArray) : Node() {

    override fun write(sb: SpecBuilder, name: String) {
        sb.append("$name ${value.asSequence().specStr("Colour")}")
    }

    override fun toString() = "Color${value.contentToString()}"
}

class MatrixNode(val value: FloatMatrix) : Node() {

    override fun write(sb: SpecBuilder, name: String) {
        sb.append("$name ${value.asSequence().specStr("Matrix33")}")
    }

    override fun toString() = "Matrix$value"
}
