package com.globant.counter.mvp.presenter

import com.globant.counter.R
import com.globant.counter.mvp.model.CalculatorModel
import com.globant.counter.mvp.view.CalculatorView
import com.globant.counter.rx.events.*
import com.globant.counter.utils.Constants
import com.globant.counter.utils.RxBus

class CalculatorPresenter(private val model: CalculatorModel, private val view: CalculatorView) {

    fun init() {
        val activity = view.activity
        activity?.let {

            // Number pressed event
            RxBus.subscribe(it, object : OnNumberButtonPressedBusObserver() {
                override fun onEvent(value: OnNumberButtonPressed) {
                    addNumberIfPossible(value.number)
                }
            })

            // Operator pressed event
            RxBus.subscribe(it, object : OnOperatorButtonBusObserver() {
                override fun onEvent(value: OnOperatorButtonPressed) {
                    addOperatorIfPossible(value.mathOperator, value.displayContent)
                }
            })

            // Equals pressed event
            RxBus.subscribe(it, object : OnEqualsButtonPressedBusObserver() {
                override fun onEvent(value: OnEqualsButtonPressed) {
                    makeOperation(value.displayValue)
                }
            })

            // Clear pressed event
            RxBus.subscribe(it, object : OnClearButtonPressedBusObserver() {
                override fun onEvent(value: OnClearButtonPressed) {
                    model.clearDisplayContent()
                    view.setDisplayText(model.displayContent)
                }
            })

            // Dot pressed event
            RxBus.subscribe(it, object : OnDotButtonPressedBusObserver() {
                override fun onEvent(value: OnDotButtonPressed) {
                    if (canAddDot(value.displayContent))
                        view.addDot()
                }
            })
        }
    }

    private fun addNumberIfPossible(number: Number) {
        if (model.isResult) {
            view.setDisplayText(number.toString())
            model.isResult = false
        } else {
            view.addNumber(number.toString())
        }
    }

    private fun addOperatorIfPossible(mathOperator: String, displayContent: String) {
        if (!displayContent.contains(Constants.ADD_SYMBOL)
                && !displayContent.contains(Constants.SUBTRACT_SYMBOL)
                && !displayContent.contains(Constants.MULTIPLY_SYMBOL)
                && !displayContent.contains(Constants.DIVIDE_SYMBOL)) {
            model.mathOperator = mathOperator
            model.isResult = false
            view.addOperator(mathOperator)
        } else if (canAddSecondOperator(displayContent)) {
            // The first number may be negative, so in that case there may be two operators
            model.mathOperator = mathOperator
            model.isResult = false
            view.addOperator(mathOperator)
        } else {
            view.showMessage(view.context?.resources!!.getString(R.string.txt_main_activity_message_one_operator))
        }
    }

    private fun canAddSecondOperator(displayContent: String): Boolean {
        return if (displayContent.contains(Constants.SUBTRACT_SYMBOL)
                && displayContent.indexOf(Constants.SUBTRACT_SYMBOL) == 0) {
            val positiveContent = displayContent.replaceFirst(Constants.SUBTRACT_SYMBOL, Constants.EMPTY_STRING)
            (!positiveContent.contains(Constants.ADD_SYMBOL) && !positiveContent.contains(Constants.SUBTRACT_SYMBOL)
                    && !positiveContent.contains(Constants.MULTIPLY_SYMBOL) && !positiveContent.contains(Constants.DIVIDE_SYMBOL))
        } else {
            false
        }
    }

    private fun canAddDot(displayContent: String): Boolean {
        if (model.mathOperator != Constants.EMPTY_STRING) {
            val numbers = displayContent.split(model.mathOperator).toTypedArray()
            return !numbers[numbers.size - 1].contains(Constants.DOT)
        } else {
            return !displayContent.contains(Constants.DOT)
        }
    }

    private fun makeOperation(displayValue: String) {
        if (model.mathOperator != Constants.EMPTY_STRING) {
            model.displayContent = displayValue
            if (model.mathOperator == Constants.SUBTRACT_SYMBOL
                    && model.displayContent.indexOf(Constants.SUBTRACT_SYMBOL) == 0) {
                // The first number is negative
                val numbers = model.displayContent.split(model.mathOperator).toTypedArray()
                if (numbers[2] != Constants.EMPTY_STRING && numbers[1] != Constants.DOT && numbers[2] != Constants.DOT)
                    view.setDisplayText(model.makeOperation((Constants.SUBTRACT_SYMBOL + numbers[1]).toDouble(),
                            numbers[2].toDouble()).toString())
            } else {
                val numbers = model.displayContent.split(model.mathOperator).toTypedArray()
                if (numbers[1] != Constants.EMPTY_STRING && numbers[0] != Constants.DOT && numbers[1] != Constants.DOT)
                    view.setDisplayText(model.makeOperation(numbers[0].toDouble(), numbers[1].toDouble()).toString())
            }
        }
    }
}
