package com.example.task_2

import javafx.scene.control.Label
import org.opencv.core.Core
import org.opencv.core.Mat

class InvertNode : HasImageNode() {
    override fun LocalInit() {
        titleBar!!.text = "Invert"

        nodes[Link.FIRST] = Triple(firstLink!!, null, NodeTypes.IMAGE)

        (firstLink!!.children.find { it is Label } as Label).text = "image"
    }

    override fun getValue(): Mat? {
        val mat = nodes[Link.FIRST]!!.second?.getValue() as Mat? ?: return null

        val mat2 = Mat()
        mat.copyTo(mat2)
        Core.bitwise_not(mat, mat2)

        return mat2
    }

    init {
        init("OneInput.fxml")
    }

}