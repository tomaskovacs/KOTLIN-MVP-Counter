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
        if (activity != null) {

            // Number pressed event
            RxBus.subscribe(activity, object : OnNumberButtonPressedBusObserver() {
                override fun onEvent(value: OnNumberButtonPressed) {
                    if (model.isResult) {
                        view.setDisplayText(value.number.toString())
                        model.isResult = false
                    } else {
                        view.addNumber(value.number.toString())
                        model.displayContent = view.getDisplayText()
                    }
                }
            })

            // Operator pressed event
            RxBus.subscribe(activity, object : OnOperatorButtonBusObserver() {
                override fun onEvent(value: OnOperatorButtonPressed) {
                    if (!value.displayContent.contains(Constants.ADD_SYMBOL)
                            && !value.displayContent.contains(Constants.SUBTRACT_SYMBOL)
                            && !value.displayContent.contains(Constants.MULTIPLY_SYMBOL)
                            && !value.displayContent.contains(Constants.DIVIDE_SYMBOL)) {
                        model.operator = value.operator
                        model.isResult = false
                        view.addOperator(value.operator)
                    } else if (canAddSecondOperator(value.displayContent)) {
                        // The first number may be negative, so in that case there may be two operators
                        model.operator = value.operator
                        model.isResult = false
                        view.addOperator(value.operator)
                    } else {
                        view.showMessage(view.context!!.resources.getString(R.string.txt_main_activity_message_one_operator))
                    }
                }
            })

            // Equals pressed event
            RxBus.subscribe(activity, object : OnEqualsButtonPressedBusObserver() {
                override fun onEvent(value: OnEqualsButtonPressed) {
                    if (model.operator != "") {
                        model.displayContent = value.displayValue
                        if (model.operator == Constants.SUBTRACT_SYMBOL
                                && model.displayContent.indexOf(Constants.SUBTRACT_SYMBOL) == 0) {
                            // The first number is negative
                            val numbers = model.displayContent.split(model.operator).toTypedArray()
                            if (numbers[2] != "" && numbers[1] != Constants.DOT && numbers[2] != Constants.DOT)
                                view.setDisplayText(model.makeOperation((Constants.SUBTRACT_SYMBOL + numbers[1]).toDouble(),
                                        numbers[2].toDouble()).toString())
                        } else {
                            val numbers = model.displayContent.split(model.operator).toTypedArray()
                            if (numbers[1] != "" && numbers[0] != Constants.DOT && numbers[1] != Constants.DOT)
                                view.setDisplayText(model.makeOperation(numbers[0].toDouble(), numbers[1].toDouble()).toString())
                        }
                    }
                }
            })

            // Clear pressed event
            RxBus.subscribe(activity, object : OnClearButtonPressedBusObserver() {
                override fun onEvent(value: OnClearButtonPressed) {
                    model.clearDisplayContent()
                    view.setDisplayText(model.displayContent)
                }
            })

            // Dot pressed event
            RxBus.subscribe(activity, object : OnDotButtonPressedBusObserver() {
                override fun onEvent(value: OnDotButtonPressed) {
                    if (canAddDot(value.displayContent))
                        view.addDot()
                }
            })
        }
    }

    private fun canAddDot(displayContent: String): Boolean {
        if (model.operator != "") {
            val numbers = displayContent.split(model.operator).toTypedArray()
            return !numbers[numbers.size - 1].contains(Constants.DOT)
        } else {
            return !displayContent.contains(Constants.DOT)
        }
    }

    private fun canAddSecondOperator(displayContent: String): Boolean {
        return if (displayContent.contains(Constants.SUBTRACT_SYMBOL)
                && displayContent.indexOf(Constants.SUBTRACT_SYMBOL) == 0) {
            val positiveContent = displayContent.replaceFirst(Constants.SUBTRACT_SYMBOL, "")
            (!positiveContent.contains(Constants.ADD_SYMBOL) && !positiveContent.contains(Constants.SUBTRACT_SYMBOL)
                    && !positiveContent.contains(Constants.MULTIPLY_SYMBOL) && !positiveContent.contains(Constants.DIVIDE_SYMBOL))
        } else {
            false
        }
    }
}
