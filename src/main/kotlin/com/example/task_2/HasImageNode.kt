package com.example.task_2

import javafx.fxml.FXML
import javafx.scene.image.ImageView
import javafx.scene.layout.AnchorPane
import org.opencv.core.Mat

abstract class HasImageNode : DragNode() {
    override val nodeType: NodeTypes = NodeTypes.IMAGE

    @FXML
    var firstLink: AnchorPane? = null

    @FXML
    var imageView: ImageView? = null

    override fun updateNode() {
        val v = getValue() as Mat?
        if (v != null) {
            imageView!!.isVisible = true
            imageView!!.image = Utility.matToImage(v)
        }
    }
}
