package dodd

import dodd.Helpers.child
import dodd.Helpers.parseSpec
import dodd.spec.SpecBuilder
import dodd.spec.SpecParser
import java.io.File

class WorldObject(gameDir: File, name: String) {

    init {
        val file = gameDir.child("WorldObjects/$name.wot")
        val root = file.parseSpec()
    }
}
