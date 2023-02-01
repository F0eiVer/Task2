package com.example.task_2

import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.layout.AnchorPane
import org.opencv.core.Mat
import org.opencv.core.Point
import org.opencv.core.Scalar
import org.opencv.imgproc.Imgproc

class TextNode : HasImageNode() {
    @FXML
    var secondLink: AnchorPane? = null

    @FXML
    var thirdLink: AnchorPane? = null

    @FXML
    var fourthLink: AnchorPane? = null

    @FXML
    var fifthLink: AnchorPane? = null

    override fun LocalInit() {
        titleBar!!.text = "AddText"

        nodes[Link.FIRST] = Triple(firstLink!!, null, NodeTypes.IMAGE)
        nodes[Link.SECOND] = Triple(secondLink!!, null, NodeTypes.INT)
        nodes[Link.THIRD] = Triple(thirdLink!!, null, NodeTypes.INT)
        nodes[Link.FOURTH] = Triple(fourthLink!!, null, NodeTypes.STRING)
        nodes[Link.FIFTH] = Triple(fifthLink!!, null, NodeTypes.FLOAT)

        (firstLink!!.children.find { it is Label } as Label).text = "image"
        (secondLink!!.children.find { it is Label } as Label).text = "int_x"
        (thirdLink!!.children.find { it is Label } as Label).text = "int_y"
        (fourthLink!!.children.find { it is Label } as Label).text = "string"
        (fifthLink!!.children.find { it is Label } as Label).text = "scale_f"
    }

    override fun getValue(): Mat? {
        val mat = nodes[Link.FIRST]!!.second?.getValue() as Mat? ?: return null
        val x = nodes[Link.SECOND]!!.second?.getValue() as Int? ?: return null
        val y = nodes[Link.THIRD]!!.second?.getValue() as Int? ?: return null
        val str = nodes[Link.FOURTH]!!.second?.getValue() as String? ?: return null
        val scale = nodes[Link.FIFTH]!!.second?.getValue() as Float? ?: return null

        val mat2 = Mat()
        mat.copyTo(mat2)

        Imgproc.putText(
            mat2,
            str,
            Point(x.toDouble(), y.toDouble()),
            0,
            scale.toDouble(),
            Scalar(255.0, 255.0, 255.0),
            scale.toInt()
        )

        return mat2
    }

    init {
        init("FiveInput.fxml")
    }
}