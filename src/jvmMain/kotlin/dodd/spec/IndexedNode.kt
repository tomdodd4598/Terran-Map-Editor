package dodd.spec

abstract class IndexedNode(val value: Array<Node>) : Node() {

    inline operator fun <reified T: Node> get(index: Int): T? {
        val node = value[index]
        return if (node is T) node else null
    }

    override fun toString() = "${javaClass.simpleName}${value.contentDeepToString()}"
}

class ArrayNode(value: Array<Node>) : IndexedNode(value) {

    override fun write(sb: SpecBuilder, name: String) {
        sb.append("$name - Size Int ${value.size}")

        val nodeName = "$name - Element"
        for (node in value) {
            node.write(sb, nodeName)
        }
    }
}

abstract class ListNode(value: Array<Node>, private val intName: String, private val nodeName: String) : IndexedNode(value) {

    override fun write(sb: SpecBuilder, name: String) {
        sb.append("$intName Int ${value.size}")

        for (node in value) {
            node.write(sb, nodeName)
        }
    }
}

class PlayerListNode(value: Array<Node>) : ListNode(value, "PlayerList", "Player")

class WorldObjectsListNode(value: Array<Node>) : ListNode(value, "WorldObjects", "WorldObject")

class PointsListNode(value: Array<Node>) : ListNode(value, "Points", "Points")

class GroupsListNode(value: Array<Node>) : ListNode(value, "Num Groups", "Group")

class RuleListNode(value: Array<Node>) : ListNode(value, "Rule List", "Rule")

class ConditionListNode(value: Array<Node>) : ListNode(value, "NumConditions", "Condition List")

class ActionListNode(value: Array<Node>) : ListNode(value, "NumActions", "Action List")
