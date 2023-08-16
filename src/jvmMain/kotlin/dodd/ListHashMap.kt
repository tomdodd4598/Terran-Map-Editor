package dodd

class ListHashMap<K, V>(vararg pairs: Pair<K, V>) {

    private val list = mutableListOf<Pair<K, V>>()
    private val map = mutableMapOf<K, Pair<Int, V>>()

    val size get() = list.size

    init {
        pairs.forEachIndexed { index, pair ->
            list.add(pair)
            map[pair.first] = Pair(index, pair.second)
        }
    }

    operator fun get(key: K) = map[key]!!.second

    operator fun get(key: Int) = list[key].second

    operator fun set(key: K, value: V) {
        val previous = map[key]
        if (previous == null) {
            val index = size
            list.add(Pair(key, value))
            map[key] = Pair(index, value)
        }
        else {
            val (index) = previous
            list[index] = Pair(key, value)
            map[key] = Pair(index, value)
        }
    }

    operator fun iterator() = list.iterator()
}
