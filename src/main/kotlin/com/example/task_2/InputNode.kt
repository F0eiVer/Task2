package com.example.task_2

import javafx.fxml.FXML
import javafx.scene.control.TextField

abstract class InputNode : DragNode() {
    @FXML
    var valueField: TextField? = null

    init {
        init("InputNode.fxml")
    }

    override fun updateNode() {}

    override fun toData(): NodeData {
        val data = super.toData()
        data.data = valueField!!.text
        return data
    }

    override fun fromData(nodeData: NodeData) {
        super.fromData(nodeData)
        valueField!!.text = nodeData.data
    }
}