package com.globant.counter.mvp.model

import com.globant.counter.utils.Constants
import com.globant.counter.utils.Operation

class CalculatorModel {

    lateinit var displayContent: String
    var mathOperator: String = Constants.EMPTY_STRING
    var isResult: Boolean = true

    fun makeOperation(firstNumber: Double, secondNumber: Double): Double {
        val result = Operation.makeOperation(firstNumber, mathOperator, secondNumber)
        displayContent = result.toString()
        clearOperator()
        return result
    }

    private fun clearOperator() {
        mathOperator = Constants.EMPTY_STRING
        isResult = true
    }

    fun clearDisplayContent() {
        displayContent = Constants.ZERO
        isResult = true
    }
}
