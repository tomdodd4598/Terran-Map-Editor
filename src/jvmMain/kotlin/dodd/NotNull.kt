package dodd

import kotlin.reflect.KProperty

class NotNull<T> {

    private var internal: T? = null

    operator fun getValue(thisRef: Any?, property: KProperty<*>) = internal!!

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        internal = value
    }
}
