package dodd

import androidx.compose.ui.unit.dp

object Global {
    val scrollbarThickness = 8.dp

    val whitespaceRegex = Regex("\\s+")

    val gameDirChildren = setOf("Effects", "Fonts", "Movies", "Strings", "World", "WorldObjects")

    val validLangs = setOf("English", "French", "German", "Italian", "Spanish")
}
