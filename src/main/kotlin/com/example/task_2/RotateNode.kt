package com.example.task_2

import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.layout.AnchorPane
import org.opencv.core.Mat
import org.opencv.core.Point
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc

class RotateNode : HasImageNode() {
    @FXML
    var secondLink: AnchorPane? = null

    override fun LocalInit() {
        titleBar!!.text = "Rotate"

        nodes[Link.FIRST] = Triple(firstLink!!, null, NodeTypes.IMAGE)
        nodes[Link.SECOND] = Triple(secondLink!!, null, NodeTypes.FLOAT)

        (firstLink!!.children.find { it is Label } as Label).text = "image"
        (secondLink!!.children.find { it is Label } as Label).text = "degree_f"
    }

    override fun getValue(): Mat? {
        val mat = nodes[Link.FIRST]!!.second?.getValue() as Mat? ?: return null
        val deg = nodes[Link.SECOND]!!.second?.getValue() as Float? ?: return null

        val mat2 = Mat()
        mat.copyTo(mat2)

        val rotMat = Imgproc.getRotationMatrix2D(Point(mat.cols() / 2.0, mat.rows() / 2.0), deg.toDouble(), 1.0)

        Imgproc.warpAffine(mat, mat2, rotMat, Size(mat.cols().toDouble(), mat.rows().toDouble()))

        return mat2
    }

    init {
        init("TwoInput.fxml")
    }
}