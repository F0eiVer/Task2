package com.example.task_2

import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.layout.AnchorPane
import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc

class ScaleNode : HasImageNode() {
    @FXML
    var secondLink: AnchorPane? = null

    @FXML
    var thirdLink: AnchorPane? = null
    override fun LocalInit() {
        titleBar!!.text = "Scale"

        nodes[Link.FIRST] = Triple(firstLink!!, null, NodeTypes.IMAGE)
        nodes[Link.SECOND] = Triple(secondLink!!, null, NodeTypes.FLOAT)
        nodes[Link.THIRD] = Triple(thirdLink!!, null, NodeTypes.FLOAT)

        (firstLink!!.children.find { it is Label } as Label).text = "image"
        (secondLink!!.children.find { it is Label } as Label).text = "float_x"
        (thirdLink!!.children.find { it is Label } as Label).text = "float_y"
    }

    override fun getValue(): Mat? {
        val mat = nodes[Link.FIRST]!!.second?.getValue() as Mat? ?: return null
        val px = nodes[Link.SECOND]!!.second?.getValue() as Float? ?: return null
        val py = nodes[Link.THIRD]!!.second?.getValue() as Float? ?: return null

        val x = mat.cols() * px / 100
        val y = mat.rows() * py / 100

        if (x <= 0 || y <= 0)
            return null

        val mat2 = Mat()
        mat.copyTo(mat2)
        Imgproc.resize(mat, mat2, Size(x.toDouble(), y.toDouble()))

        return mat2
    }

    init {
        init("ThreeInput.fxml")
    }

}
