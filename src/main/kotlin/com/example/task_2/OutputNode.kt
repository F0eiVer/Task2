package com.example.task_2

import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.stage.FileChooser
import org.opencv.core.Mat
import org.opencv.imgcodecs.Imgcodecs
import java.io.IOException


class OutputNode : HasImageNode() {
    @FXML
    var saveButton: Button? = null

    override fun getValue(): Mat? {
        return nodes["firstLink"]!!.second?.getValue() as Mat? ?: return null
    }

    override fun LocalInit() {
        rootPane!!.onDragDetected = null

        nodes["firstLink"] = Triple(firstLink!!, null, NodeTypes.IMAGE)

        saveButton!!.onAction = EventHandler {
            val mat = nodes["firstLink"]!!.second?.getValue() as Mat? ?: return@EventHandler

            val fileChooser = FileChooser()
            fileChooser.title = "Save Picture"
            fileChooser.extensionFilters.addAll(FileChooser.ExtensionFilter("Image Files", "*.png"))
            val dir = fileChooser.showSaveDialog(scene.window)
            if (dir != null) {
                try {
                    Imgcodecs.imwrite(dir.absolutePath, mat)
                } catch (e: IOException) {
                    println(e)
                }
            }
        }
    }

    override fun updateNode() {
        val v = getValue()
        if (v != null) {
            imageView!!.isVisible = true
            imageView!!.image = Utility.matToImage(v)
        }
    }

    init {
        init("OutputNode.fxml")
    }
}