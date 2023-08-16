package dodd

import dodd.Helpers.child
import dodd.Helpers.parseSpec
import java.io.File

class World(gameDir: File, name: String) {

    init {
        val file = gameDir.child("World/$name.twt")
        val root = file.parseSpec()
    }
}
