package com.example.task_2

class FloatNode : InputNode() {

    override val nodeType: NodeTypes = NodeTypes.FLOAT

    override fun LocalInit() {
        valueField!!.text = "0.0"
        titleBar!!.text = "Float"

        valueField!!.textProperty().addListener { i, j, k ->
            updateNode()
            outputLink?.kickAction()
        }
    }

    override fun getValue(): Float? {
        return valueField!!.text.toFloatOrNull()
    }
}