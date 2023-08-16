package dodd

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import dodd.Global.gameDirChildren
import dodd.Global.scrollbarThickness
import dodd.Global.validLangs
import dodd.Helpers.letIfNotNull
import java.io.File

private val config = Config("config.txt", Pair("gameDir", ""), Pair("lang", "English"))

private var locale by NotNull<Locale>()

fun File.isValidGameDir() = isDirectory && list()!!.toSet().containsAll(gameDirChildren)

fun Modifier.scroll(verticalState: ScrollState? = null, horizontalState: ScrollState? = null) = letIfNotNull(verticalState) { verticalScroll(it) }.letIfNotNull(horizontalState) { horizontalScroll(it) }

@Composable
fun BoxScope.BasicScrollbars(color: Color, horizontalScrollState: ScrollState? = null, verticalScrollState: ScrollState? = null) {
    horizontalScrollState?.let {
        HorizontalScrollbar(modifier = Modifier.background(color).align(Alignment.BottomStart).fillMaxWidth().padding(end = scrollbarThickness), adapter = rememberScrollbarAdapter(it))
    }
    verticalScrollState?.let {
        VerticalScrollbar(modifier = Modifier.background(color).align(Alignment.CenterEnd).fillMaxHeight(), adapter = rememberScrollbarAdapter(it))
    }
}

@Composable
fun App(root: ApplicationScope) {

    // SETUP GAME DIR

    var gameDirProperty by config.delegate("gameDir")
    var gameDirStr by remember { mutableStateOf(gameDirProperty) }
    val gameDir by remember { derivedStateOf { File(gameDirStr) } }

    var gameDirDialogOpen by remember { mutableStateOf(!gameDir.isValidGameDir()) }

    if (gameDirDialogOpen) {
        fun onCloseRequest() {
            val valid = gameDir.isValidGameDir()
            if (valid) {
                gameDirProperty = gameDirStr
                gameDirDialogOpen = false
            }
            else {
                root.exitApplication()
            }
        }

        Dialog(onCloseRequest = ::onCloseRequest, title = "Set Game Directory") {
            Box(modifier = Modifier.background(Color.Gray).fillMaxSize()) {
                OutlinedTextField(value = gameDirStr, onValueChange = { gameDirStr = it }, label = { Text("Enter Text") }, singleLine = true)
            }
        }
    }

    // INIT GAME INFO

    val initPaused by remember { derivedStateOf { gameDirDialogOpen } }

    var initGameInfo by remember { mutableStateOf(false) }

    var langProperty by config.delegate("lang")

    if (!initPaused && !initGameInfo) {
        if (!validLangs.contains(langProperty)) {
            langProperty = config.default("lang")
        }
        locale = Locale(gameDir, langProperty)

        World(gameDir, "Border_Dispute_Navy")

        initGameInfo = true
    }

    // MAIN INTERFACE

    var mapZoom by remember { mutableStateOf(100f) }
    var mapWidth by remember { mutableStateOf(200f) }
    var mapHeight by remember { mutableStateOf(200f) }

    fun Float.mapUnits() = (mapZoom * this).dp

    Column(modifier = Modifier.background(Color.Gray)) {
        Box(modifier = Modifier.background(Color.DarkGray).fillMaxWidth().fillMaxHeight(0.125f)) {

        }

        Box(modifier = Modifier.fillMaxWidth().height(scrollbarThickness))

        Row {
            Box(modifier = Modifier.fillMaxWidth(0.25f).fillMaxHeight()) {
                val horizontalScrollState = rememberScrollState(0)
                val verticalScrollState = rememberScrollState(0)

                BasicScrollbars(Color.Gray, horizontalScrollState, verticalScrollState)
            }

            Box(modifier = Modifier.fillMaxSize()) {
                val horizontalScrollState = rememberScrollState(0)
                val verticalScrollState = rememberScrollState(0)

                Box(modifier = Modifier.fillMaxSize().scroll(horizontalScrollState, verticalScrollState)) {
                    Canvas(modifier = Modifier.size(mapWidth.mapUnits(), mapHeight.mapUnits())) {
                        drawRect(color = Color.DarkGray, size = size)
                    }
                }

                BasicScrollbars(Color.Gray, horizontalScrollState, verticalScrollState)
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Terran Map Editor") {
        App(this@application)
    }
}
