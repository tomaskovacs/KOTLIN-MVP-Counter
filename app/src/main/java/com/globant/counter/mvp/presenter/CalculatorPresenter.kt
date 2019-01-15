package com.globant.counter.mvp.presenter

import com.globant.counter.R
import com.globant.counter.mvp.model.CalculatorModel
import com.globant.counter.mvp.view.CalculatorView
import com.globant.counter.rx.events.OnEqualsButtonPressedBusObserver
import com.globant.counter.rx.events.OnNumberButtonPressedBusObserver
import com.globant.counter.rx.events.OnOperatorButtonBusObserver
import com.globant.counter.utils.MathUtils
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
                        view.setDisplayText(view.getDisplayText() + value.number.toString())
                        model.displayContent = view.getDisplayText()
                    }
                }
            })

            // Operator pressed event
            RxBus.subscribe(activity, object : OnOperatorButtonBusObserver() {
                override fun onEvent(value: OnOperatorButtonPressed) {
                    if (!value.displayContent.contains("+") && !value.displayContent.contains("-")
                            && !value.displayContent.contains("*") && !value.displayContent.contains("/")) {
                        model.operator = value.operator
                        view.setDisplayText(value.displayContent + value.operator)
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
                        view.setDisplayText(makeOperation())
                    } else {
                        view.showMessage(view.context!!.resources.getString(R.string.txt_main_activity_message_use_operator))
                    }
                }
            })
        }
    }

    fun makeOperation(): String {
        val numbers = model.displayContent.split(model.operator).toTypedArray()
        if (numbers[1] != "") {
            val result = MathUtils.makeOperation(numbers[0].toDouble(), model.operator, numbers[1].toDouble()).toString()
            model.operator = ""
            model.isResult = true
            return result
        } else {
            view.showMessage(view.context!!.resources.getString(R.string.txt_main_activity_message_add_second_parameter))
            return model.displayContent
        }
    }
}
