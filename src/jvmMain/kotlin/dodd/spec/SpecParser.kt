package dodd.spec

import dodd.FloatMatrix
import dodd.Helpers.filterNotEmpty
import dodd.Helpers.peek
import dodd.Helpers.pop
import dodd.Helpers.push
import dodd.Helpers.splitByWhitespace
import java.io.File

class SpecParser(private val lines: Iterable<String>) {

    constructor(file: File) : this(file.readLines())

    private val stack = ArrayDeque<StackElement>()

    fun result(): RootNode {
        val root = RootNode()
        stack.push(root)
        parse(NodeParseIterator(lines.asSequence().map { it.trim() }.filterNotEmpty().iterator()))
        stack.pop()
        return root
    }

    private fun parse(iter: NodeParseIterator) {
        val elem = stack.peek()

        fun nextLine() = iter.tryNext(elem, false).first

        fun nextLineLast() = nextLine().splitByWhitespace().last()

        fun trimSplit(line: String, delim: String) = line.splitToSequence(delim).map { it.trim() }.toList()

        fun stringValue(line: String) = line.split("'").let { it[it.size - 2] }

        fun boolNode(str: String) = BoolNode(str.toBoolean())

        fun intNode(str: String) = IntNode(str.toInt())

        fun floatNode(str: String) = FloatNode(str.toFloat())

        fun doubleNode(str: String) = DoubleNode(str.toDouble())

        fun nestedName(line: String, first: String?) = line.substring((first ?: line.splitByWhitespace().first()).length).trim()

        fun pushParsePop(innerElement: StackElement) {
            stack.push(innerElement)
            parse(iter)
            stack.pop()
        }

        fun parseNextNested(nested: NestedNode) {
            nextLine() // {
            pushParsePop(nested)
        }

        fun floatTupleSplit(line: String, type: String): Pair<String, Sequence<Float>> {
            val (name, str) = trimSplit(line, type)
            return Pair(name, str.split("(", ")")[1].splitToSequence(",").map { it.toFloat() })
        }

        while (iter.hasNext()) {
            val (line, valid) = iter.tryNext(elem, true)
            if (!valid || line == "}") {
                break
            }

            val splitWhitespace = line.splitByWhitespace()
            val firstWord = splitWhitespace.first()
            val lastWord = splitWhitespace.last()

            fun parseNextList(name: String, constructor: (Int) -> ListElement) {
                val listElem = constructor(lastWord.toInt())
                pushParsePop(listElem)
                elem[name] = listElem.node()
            }

            fun scalarName(type: String) = line.substring(0 until line.substring(0 until line.length - lastWord.length).lastIndexOf(type)).trim()

            // # ...
            if (line.startsWith("#")) {
                // Fudge part 1
                elem[line] = RawNode(line)
            }

            // Rule Name String '...'
            else if (elem is RuleListElement && line.startsWith("Rule Name String")) {
                val name = stringValue(line)
                val ruleNamePair = Pair("Rule Name", StringNode(name))
                val runOncePair = Pair("Run Once", boolNode(nextLineLast()))
                val isActivePair = Pair("Is Active", boolNode(nextLineLast()))

                val conditionList = ConditionListElement(nextLineLast().toInt())
                pushParsePop(conditionList)
                val conditionListPair = Pair("Conditions", conditionList.node())

                val actionList = ActionListElement(nextLineLast().toInt())
                pushParsePop(actionList)
                val actionListPair = Pair("Actions", actionList.node())

                elem[name] = TupleNode(ruleNamePair, runOncePair, isActivePair, conditionListPair, actionListPair)
            }

            // 0... Condition List
            else if (elem is ConditionListElement && line.contains("Condition List")) {
                val condition = ConditionNode()
                parseNextNested(condition)
                elem["Condition List"] = condition
            }

            // 0... Action List
            else if (elem is ActionListElement && line.contains("Action List")) {
                val action = ActionNode()
                parseNextNested(action)
                elem["Action List"] = action
            }

            // ID Int ...
            else if (elem is WorldObjectsListElement && line.startsWith("ID Int")) {
                val idPair = Pair("ID", intNode(lastWord))
                val typePair = Pair("Type", StringNode(stringValue(nextLine())))

                val nestName = nestedName(nextLine(), null)
                val nested = NestedNode()
                parseNextNested(nested)
                val nestedPair = Pair(nestName, nested)

                elem["WorldObject"] = TupleNode(idPair, typePair, nestedPair)
            }

            // Type String '...'
            else if (elem !is ConditionNode && elem !is ActionNode && line.startsWith("Type String")) {
                val name = stringValue(line)
                val empty = name.isEmpty()
                val rawPrevious = elem.rawPrevious()
                val modifiedName = if (rawPrevious == null) name else "${rawPrevious}Type"
                if (empty && rawPrevious != null) {
                    // Fudge part 2
                    elem[modifiedName] = TupleNode(Pair("Type", StringNode("")))
                }
                else if (!empty) {
                    val typePair = Pair("Type", StringNode(name))

                    val nestName = nestedName(nextLine(), null)
                    val nested = NestedNode()
                    parseNextNested(nested)
                    val nestedPair = Pair(nestName, nested)

                    elem[modifiedName] = TupleNode(typePair, nestedPair)
                }
            }

            // Definition String '...'
            else if (line.startsWith("Definition String")) {
                val name = stringValue(line)
                if (name.isNotEmpty()) {
                    val pairList = mutableListOf<Pair<String, Node>>()

                    pairList.add(Pair("Definition", StringNode(name)))
                    val entity = stringValue(nextLine())
                    pairList.add(Pair("EntityType", StringNode(entity)))
                    if (entity.isNotEmpty()) {
                        pairList.add(Pair("FactoryType", StringNode(stringValue(nextLine()))))

                        val nestName = nestedName(nextLine(), null)
                        val nested = NestedNode()
                        parseNextNested(nested)
                        pairList.add(Pair(nestName, nested))
                    }

                    elem[name] = TupleNode(*pairList.toTypedArray())
                }
            }

            // ... - Size Int ...
            else if (line.contains("- Size Int")) {
                val (name, size) = trimSplit(line, "- Size Int")
                val arrayElem = ArrayElement(size.toInt())
                pushParsePop(arrayElem)
                elem[name] = arrayElem.node()
            }

            // PlayerInfo - Player Name String '...'
            else if (line.startsWith("PlayerInfo - Player Name String")) {
                val name = stringValue(line)
                val playerNamePair = Pair("PlayerInfo - Player Name", StringNode(name))
                val teamIndexPair = Pair("PlayerInfo - TeamIndex", intNode(nextLineLast()))
                elem[name] = TupleNode(playerNamePair, teamIndexPair)
            }

            // PlayerList Int ...
            else if (line.startsWith("PlayerList Int")) {
                parseNextList("PlayerList", ::PlayerListElement)
            }

            // WorldObjects Int ...
            else if (line.startsWith("WorldObjects Int")) {
                parseNextList("WorldObjects", ::WorldObjectsListElement)
            }

            // Points Int ...
            else if (line.startsWith("Points Int")) {
                parseNextList("Points", ::PointsListElement)
            }

            // Num Groups Int ...
            else if (line.startsWith("Num Groups Int")) {
                parseNextList("Num Groups", ::GroupsListElement)
            }

            // Rule List Int ...
            else if (line.startsWith("Rule List Int")) {
                parseNextList("Rule List", ::RuleListElement)
            }

            // 0... ...
            else if (firstWord.toIntOrNull() != null) {
                val name = nestedName(line, firstWord)
                val nested = if (name == "CurrentMix" || name == "UserMix") MixNode() else NestedNode()
                parseNextNested(nested)
                elem[name] = nested
            }

            // ... Matrix33( ..., ..., ..., ..., ..., ..., ..., ..., ... )
            else if (line.contains("Matrix33")) {
                val (name, floats) = floatTupleSplit(line, "Matrix33")
                elem[name] = MatrixNode(FloatMatrix(floats.toList().toFloatArray(), 3))
            }

            // ... Colour( ..., ..., ..., ... )
            else if (line.contains("Colour")) {
                val (name, floats) = floatTupleSplit(line, "Colour")
                elem[name] = ColorNode(floats.toList().toFloatArray())
            }

            // ... Vector3( ..., ..., ... )
            else if (line.contains("Vector3")) {
                val (name, floats) = floatTupleSplit(line, "Vector3")
                elem[name] = VectorNode(floats.toList().toFloatArray())
            }

            // ... Coord( ..., ... )
            else if (line.contains("Coord")) {
                val (name, floats) = floatTupleSplit(line, "Coord")
                elem[name] = CoordNode(floats.toList().toFloatArray())
            }

            // ... String '...'
            else if (lastWord.contains("'")) {
                elem[scalarName("String")] = StringNode(stringValue(line))
            }

            else {
                when (splitWhitespace[splitWhitespace.size - 2]) {
                    // ... Bool ...
                    "Bool" -> elem[scalarName("Bool")] = boolNode(lastWord)

                    // ... Int ...
                    "Int" -> elem[scalarName("Int")] = intNode(lastWord)

                    // ... Float ...
                    "Float" -> elem[scalarName("Float")] = floatNode(lastWord)

                    // ... Double ...
                    "Double" -> elem[scalarName("Double")] = doubleNode(lastWord)
                }
            }
        }
    }
}
