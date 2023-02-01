package com.example.task_2

import javafx.application.Application
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.stage.Stage
import com.google.gson.Gson
import javafx.stage.FileChooser
import org.opencv.core.Core
import java.io.File
import java.io.IOException

fun createNode(buttonType: ButtonsAndNodesTypes): DragNode {
    return when (buttonType) {
        ButtonsAndNodesTypes.INT -> IntNode()
        ButtonsAndNodesTypes.FLOAT -> FloatNode()
        ButtonsAndNodesTypes.STRING -> StringNode()
        ButtonsAndNodesTypes.IMAGE -> ImageNode()
        ButtonsAndNodesTypes.SEPIA -> SepiaNode()
        ButtonsAndNodesTypes.GREY -> GreyNode()
        ButtonsAndNodesTypes.INVERT -> InvertNode()
        ButtonsAndNodesTypes.BRIGHTNESS -> BrightnessNode()
        ButtonsAndNodesTypes.GAUSS -> GaussNode()
        ButtonsAndNodesTypes.SCALE -> ScaleNode()
        ButtonsAndNodesTypes.MOVE -> MoveNode()
        ButtonsAndNodesTypes.ADDTEXT -> TextNode()
        ButtonsAndNodesTypes.ROTATE -> RotateNode()
        ButtonsAndNodesTypes.OUTPUT -> OutputNode()
        else -> IntNode()
    }

}

fun addButton(box: VBox, workSpace : Pane, button: ButtonsAndNodesTypes) {

    when (button) {
        ButtonsAndNodesTypes.NONE -> return
    }

    val btn = Button(button.toString().lowercase())
    btn.style = buttonStyle

    btn.onAction = EventHandler {
        val node = createNode(button)
        node.layoutX += workSpace.width * 0.14
        node.layoutY += workSpace.height * 0.05
        workSpace.children.add(node)
    }
    box.children.add(btn)
}

class MainScreen{
    val root = AnchorPane()
    val scene = Scene(root, 1200.0, 600.0)
    val listOfNodes = VBox()

    fun Start(): Scene{

        for (i in buttonsList){
            if(i == ButtonsAndNodesTypes.OUTPUT)
                continue
            addButton(listOfNodes, root, i)
        }

        root.style = "-fx-background-color: grey;"

        listOfNodes.spacing = 5.0
        listOfNodes.padding = Insets(5.0, 5.0, 5.0, 5.0)
        listOfNodes.style = "-fx-background-color: grey;" +
                "-fx-border-color: black;" +
                "-fx-border-width: 3px;"
        listOfNodes.minWidth = 150.0
        listOfNodes.minHeight = scene.height

        root.children.add(listOfNodes)

        val start = createNode(ButtonsAndNodesTypes.IMAGE)
        start.layoutX = scene.width * 0.14
        start.layoutY = 0.0
        root.children.add(start)
        start.layoutBoundsProperty()

        val end = OutputNode()
        end.layoutX = scene.width * 0.9
        end.layoutY = scene.height * 0.65
        root.children.add(end)

        var saveLoadButtons = saveLoadButtons(150.0, 560.0)
        saveLoadButtons.spacing = 5.0
        saveLoadButtons.padding = Insets(5.0, 5.0, 5.0, 5.0)
        root.children.add(saveLoadButtons)

        return scene
    }

    fun saveLoadButtons(x: Double, y: Double): HBox {
        val hBox = HBox()
        hBox.layoutX = x
        hBox.layoutY = y

        val btn1 = Button("Save Scene")
        btn1.style = buttonStyle
        btn1.onAction = EventHandler {
            val gson = Gson()
            val nodes = root.children.filterIsInstance<DragNode>()
            val listNodes = MutableList(nodes.size) { nodes[it].toData() }
            val links = root.children.filterIsInstance<NodeLink>()
            val listLinks = MutableList(links.size) { links[it].toData() }

            println(gson.toJson(Saved(listNodes, listLinks)))

            val fileChooser = FileChooser()
            fileChooser.title = "Save Nodes"
            fileChooser.extensionFilters.addAll(FileChooser.ExtensionFilter("Node Files", "*.ns"))
            val dir = fileChooser.showSaveDialog(scene.window)
            if (dir != null) {
                try {
                    val file = File(dir.toURI())
                    file.writeText(gson.toJson(Saved(listNodes, listLinks)))
                } catch (e: IOException) {
                    println(e)
                }
            }
        }
        hBox.children.add(btn1)

        val btn2 = Button("Load Scene")
        btn2.style = buttonStyle
        btn2.onAction = EventHandler {
            val fileChooser = FileChooser()
            fileChooser.title = "Load Nodes"
            fileChooser.extensionFilters.addAll(FileChooser.ExtensionFilter("Node Files", "*.ns"))
            val dir = fileChooser.showOpenDialog(scene.window)
            if (dir != null) {
                try {
                    val file = File(dir.toURI())
                    if (!file.exists()) return@EventHandler

                    val data = Gson().fromJson(file.readText(), Saved::class.java)
                    if (data.links == null || data.nodes == null) return@EventHandler

                    root.children.removeIf { it is DragNode || it is NodeLink }

                    data.nodes.forEach {
                        val node = createNode(toNeedType(it.name))
                        node.fromData(it)
                        root.children.add(node)
                    }

                    data.links.forEach {
                        val inNode = root.lookup("#${it.inputNode}") as DragNode
                        val outNode = root.lookup("#${it.outputNode}") as DragNode
                        val inAnchor = root.lookup("#${it.inputAnchor}") as AnchorPane
                        val outAnchor = root.lookup("#${it.outputAnchor}") as AnchorPane

                        inAnchor.layoutX = it.inputAnchorSize.first
                        inAnchor.layoutY = it.inputAnchorSize.second

                        outAnchor.layoutX = it.outputAnchorSize.first
                        outAnchor.layoutY = it.outputAnchorSize.second

                        inNode.linkNodes(outNode, inNode, outAnchor, inAnchor, it.inputAnchor!!).id = it.id
                    }

                } catch (e: IOException) {
                    println(e)
                }
            }
        }
        hBox.children.add(btn2)

        return hBox
    }
}


data class Saved(val nodes: MutableList<NodeData>?, val links: MutableList<LinkData>?)

class Program : Application() {
    override fun start(primaryStage: Stage) {
        nu.pattern.OpenCV.loadLocally()
        primaryStage.scene = MainScreen().Start()
        primaryStage.title = "Node Photo Redactor"
        primaryStage.show()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(Program::class.java)
        }
    }
}