package com.example.task_2

class StringNode : InputNode() {
    override val nodeType: NodeTypes = NodeTypes.STRING

    override fun LocalInit() {
        valueField!!.text = ""
        titleBar!!.text = "String"

        valueField!!.textProperty().addListener { i, j, k ->
            updateNode()
            outputLink?.kickAction()
        }
    }

    override fun getValue(): String {
        return valueField!!.text
    }
}