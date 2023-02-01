package com.example.task_2

import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.input.*
import javafx.stage.FileChooser
import org.opencv.core.Mat
import org.opencv.imgcodecs.Imgcodecs
import java.io.File


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

    fun copyEvent(){

        copyKey = EventHandler {
            println("hoo")
            if(it.isControlDown && it.code == KeyCode.C){
                val clipboard: Clipboard = Clipboard.getSystemClipboard()
                val content = ClipboardContent()
                content.putImage(imageView!!.image);
                content.putFiles(java.util.Collections.singletonList(File(path)));
                clipboard.setContent(content);
            }
            it.consume()
        }
        println("hiiii")
    }

    override fun LocalInit() {

        copyEvent()

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