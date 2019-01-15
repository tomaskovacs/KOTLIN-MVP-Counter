package com.globant.counter.utils

class MathUtils {

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
                "+" -> addition(firstNumber, secondNumber)
                "-" -> subtraction(firstNumber, secondNumber)
                "*" -> multiplication(firstNumber, secondNumber)
                "/" -> division(firstNumber, secondNumber)
                else -> 0.0
            }
        }
    }
}
