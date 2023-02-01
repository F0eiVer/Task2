package com.example.task_2

class IntNode : InputNode() {
    override val nodeType: NodeTypes = NodeTypes.INT

    override fun LocalInit() {
        valueField!!.text = "0"
        titleBar!!.text = "Integer"

        valueField!!.textProperty().addListener { i, j, k ->
            updateNode()
            outputLink?.kickAction()
        }
    }

    override fun getValue(): Int? {
        return valueField!!.text.toIntOrNull()
    }

}