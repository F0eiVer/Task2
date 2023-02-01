package com.example.task_2

import javafx.scene.control.Label
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc

class GreyNode : HasImageNode() {
    override fun LocalInit() {
        titleBar!!.text = "Grey"

        nodes[Link.FIRST] = Triple(firstLink!!, null, NodeTypes.IMAGE)

        (firstLink!!.children.find { it is Label } as Label).text = "image"
    }

    override fun getValue(): Mat? {
        val mat = nodes[Link.FIRST]!!.second?.getValue() as Mat? ?: return null

        val mat2 = Mat()
        mat.copyTo(mat2)
        Imgproc.cvtColor(mat, mat2, Imgproc.COLOR_BGR2GRAY)

        val mat3 = Mat()

        Core.merge(List(3) { mat2 }, mat3)

        return mat3
    }

    init {
        init("OneInput.fxml")
    }
}