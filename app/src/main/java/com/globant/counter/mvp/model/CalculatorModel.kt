package com.globant.counter.mvp.model

import com.globant.counter.utils.MathUtils

class CalculatorModel {

    lateinit var displayContent: String
    var operator: String = ""
    var isResult: Boolean = true

    fun makeOperation(firstNumber: Double, secondNumber: Double): Double {
        val result = MathUtils.makeOperation(firstNumber, operator, secondNumber)
        displayContent = result.toString()
        clearOperator()
        return result
    }

    private fun clearOperator() {
        operator = ""
        isResult = true
    }

    fun clearDisplayContent() {
        displayContent = "0"
        isResult = true
    }
}
