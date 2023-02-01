package com.example.task_2

import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.stage.FileChooser
import org.opencv.core.Mat
import org.opencv.imgcodecs.Imgcodecs

class ImageNode : HasImageNode() {
    override val nodeType: NodeTypes = NodeTypes.IMAGE

    @FXML
    var openButton: Button? = null

    private var imageMat: Mat? = null
    private var path: String? = null

    override fun getValue(): Mat? {
        return imageMat
    }

    fun getImage() {
        imageMat = Imgcodecs.imread(path)
        updateNode()
        imageView!!.isVisible = true
        outputLink?.kickAction()
    }

    override fun LocalInit() {
        openButton!!.onAction = EventHandler {
            val fileChooser = FileChooser()
            fileChooser.title = "Add image"
            fileChooser.extensionFilters.add(FileChooser.ExtensionFilter("Image Files", "*.png"))
            val file = fileChooser.showOpenDialog(scene.window)
            if (file != null) {
                path = file.absolutePath
                getImage()
            }
        }
    }

    override fun toData(): NodeData {
        val data = super.toData()
        data.data = path
        return data
    }

    override fun fromData(nodeData: NodeData) {
        super.fromData(nodeData)
        path = nodeData.data
        getImage()
    }

    init {
        init("ImageNode.fxml")
    }
}