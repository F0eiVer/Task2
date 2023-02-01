package com.example.task_2

import javafx.scene.image.Image
import org.opencv.core.Mat
import org.opencv.core.MatOfByte
import org.opencv.imgcodecs.Imgcodecs
import java.io.ByteArrayInputStream

val buttonsList = mutableListOf<ButtonsAndNodesTypes>(ButtonsAndNodesTypes.ADDTEXT,
    ButtonsAndNodesTypes.NONE, ButtonsAndNodesTypes.BRIGHTNESS, ButtonsAndNodesTypes.GAUSS,
    ButtonsAndNodesTypes.FLOAT, ButtonsAndNodesTypes.GREY, ButtonsAndNodesTypes.IMAGE,
    ButtonsAndNodesTypes.INT, ButtonsAndNodesTypes.INVERT, ButtonsAndNodesTypes.MOVE,
    ButtonsAndNodesTypes.ROTATE, ButtonsAndNodesTypes.SCALE, ButtonsAndNodesTypes.SEPIA,
    ButtonsAndNodesTypes.STRING, ButtonsAndNodesTypes.OUTPUT)

const val buttonStyle = "-fx-background-radius: 5px;" +
        "-fx-border-radius: 5px;" +
        "-fx-border-color: #EE82EE;" +
        "-fx-background-color: #800080;" +
        "-fx-text-fill: #00FAFF;" +
        "-fx-font-size: 14px;" +
        "-fx-font-weight: bold;" +
        "-fx-font-style: italic;"


enum class NodeTypes {
    INT, FLOAT, STRING, IMAGE, NONE
}

enum class ButtonsAndNodesTypes{
    INT, FLOAT, STRING, IMAGE, SEPIA, GREY, INVERT, BRIGHTNESS, SCALE, ROTATE, MOVE, ADDTEXT, GAUSS, OUTPUT, NONE
}

fun toNeedType(str: String) : ButtonsAndNodesTypes {
    return when(str){
        IntNode::class.simpleName!! -> ButtonsAndNodesTypes.INT
        FloatNode::class.simpleName!! -> ButtonsAndNodesTypes.FLOAT
        StringNode::class.simpleName!! -> ButtonsAndNodesTypes.STRING
        ImageNode::class.simpleName!! -> ButtonsAndNodesTypes.IMAGE
        SepiaNode::class.simpleName!! -> ButtonsAndNodesTypes.SEPIA
        GreyNode::class.simpleName!! -> ButtonsAndNodesTypes.GREY
        InvertNode::class.simpleName!! -> ButtonsAndNodesTypes.INVERT
        BrightnessNode::class.simpleName!! -> ButtonsAndNodesTypes.BRIGHTNESS
        ScaleNode::class.simpleName!! -> ButtonsAndNodesTypes.SCALE
        RotateNode::class.simpleName!! -> ButtonsAndNodesTypes.ROTATE
        MoveNode::class.simpleName!! -> ButtonsAndNodesTypes.MOVE
        TextNode::class.simpleName!! -> ButtonsAndNodesTypes.ADDTEXT
        GaussNode::class.simpleName!! -> ButtonsAndNodesTypes.GAUSS
        OutputNode::class.simpleName!! -> ButtonsAndNodesTypes.OUTPUT
        else -> ButtonsAndNodesTypes.NONE
    }
}

class Link {
    companion object {
        const val FIRST = "firstLink"
        const val SECOND = "secondLink"
        const val THIRD = "thirdLink"
        const val FOURTH = "fourthLink"
        const val FIFTH = "fifthLink"
    }
}

class Utility {
    companion object {
        fun matToImage(mat: Mat): Image {
            val buffer = MatOfByte()
            Imgcodecs.imencode(".png", mat, buffer)

            return Image(ByteArrayInputStream(buffer.toArray()))
        }
    }
}