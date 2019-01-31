package com.globant.counter.utils

class Operation {

    companion object {
        fun addition(firstNumber: Double, secondNumber: Double): Double = firstNumber + secondNumber

        fun subtraction(firstNumber: Double, secondNumber: Double): Double = firstNumber - secondNumber

        fun multiplication(firstNumber: Double, secondNumber: Double): Double = firstNumber * secondNumber

        fun division(firstNumber: Double, secondNumber: Double): Double {
            return if (secondNumber != 0.0)
                firstNumber / secondNumber
            else
                0.0
        }

        fun makeOperation(firstNumber: Double, operator: String, secondNumber: Double): Double {
            return when (operator) {
                Constants.ADD_SYMBOL -> addition(firstNumber, secondNumber)
                Constants.SUBTRACT_SYMBOL -> subtraction(firstNumber, secondNumber)
                Constants.MULTIPLY_SYMBOL -> multiplication(firstNumber, secondNumber)
                Constants.DIVIDE_SYMBOL -> division(firstNumber, secondNumber)
                else -> 0.0
            }
        }
    }
}
