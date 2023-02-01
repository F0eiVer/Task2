package com.example.task_2

import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.layout.AnchorPane
import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc

class GaussNode : HasImageNode() {
    @FXML
    var secondLink: AnchorPane? = null

    override fun LocalInit() {
        titleBar!!.text = "Gauss"

        nodes[Link.FIRST] = Triple(firstLink!!, null, NodeTypes.IMAGE)
        nodes[Link.SECOND] = Triple(secondLink!!, null, NodeTypes.INT)

        (firstLink!!.children.find { it is Label } as Label).text = "image"
        (secondLink!!.children.find { it is Label } as Label).text = "int"
    }


    override fun getValue(): Mat? {
        val image = nodes[Link.FIRST]!!.second?.getValue() as Mat? ?: return null
        var kernelSize = nodes[Link.SECOND]!!.second?.getValue() as Int? ?: return null

        kernelSize = kernelSize * 2 + 1
        if (kernelSize <= 0 || kernelSize > 100)
            return null


        val newImage = Mat()
        image.copyTo(newImage)

        Imgproc.GaussianBlur(image, newImage, Size(kernelSize.toDouble(), kernelSize.toDouble()), 0.0)

        return newImage
    }

    init {
        init("TwoInput.fxml")
    }

}