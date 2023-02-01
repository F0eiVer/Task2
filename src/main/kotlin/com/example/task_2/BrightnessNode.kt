package com.example.task_2

import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.layout.AnchorPane
import org.opencv.core.Mat
import kotlin.math.roundToInt

class BrightnessNode : HasImageNode() {
    @FXML
    var secondLink: AnchorPane? = null

    override fun LocalInit() {
        titleBar!!.text = "Bright"

        nodes[Link.FIRST] = Triple(firstLink!!, null, NodeTypes.IMAGE)
        nodes[Link.SECOND] = Triple(secondLink!!, null, NodeTypes.FLOAT)

        (firstLink!!.children.find { it is Label } as Label).text = "image"
        (secondLink!!.children.find { it is Label } as Label).text = "float"
    }


    override fun getValue(): Mat? {
        fun saturate(`val`: Double): Byte {
            var iVal = `val`.roundToInt()
            iVal = if (iVal > 255) 255 else if (iVal < 0) 0 else iVal
            return iVal.toByte()
        }

        val image = nodes[Link.FIRST]!!.second?.getValue() as Mat? ?: return null
        val beta = nodes[Link.SECOND]!!.second?.getValue() as Float? ?: return null
        val alpha = 1.0

        val newImage = Mat()
        image.copyTo(newImage)

        val imageData = ByteArray(((image.total() * image.channels()).toInt()))
        image.get(0, 0, imageData)
        val newImageData = ByteArray((newImage.total() * newImage.channels()).toInt())
        for (y in 0 until image.rows()) {
            for (x in 0 until image.cols()) {
                for (c in 0 until image.channels()) {
                    var pixelValue = imageData[(y * image.cols() + x) * image.channels() + c].toDouble()
                    pixelValue = if (pixelValue < 0) pixelValue + 256 else pixelValue
                    newImageData[(y * image.cols() + x) * image.channels() + c] = saturate(alpha * pixelValue + beta)
                }
            }
        }
        newImage.put(0, 0, newImageData)

        return newImage
    }

    init {
        init("TwoInput.fxml")
    }
}