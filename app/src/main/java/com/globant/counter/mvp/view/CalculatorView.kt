package com.globant.counter.mvp.view

import android.app.Activity
import android.widget.Toast
import com.globant.counter.rx.events.OnEqualsButtonPressedBusObserver
import com.globant.counter.rx.events.OnNumberButtonPressedBusObserver
import com.globant.counter.rx.events.OnOperatorButtonBusObserver
import com.globant.counter.utils.RxBus
import kotlinx.android.synthetic.main.activity_main.*

class CalculatorView(activity: Activity) : ActivityView(activity) {

    init {
        // Number click listeners
        activity.btn_calculator_one.setOnClickListener {
            RxBus.post(OnNumberButtonPressedBusObserver.OnNumberButtonPressed(1))
        }
        activity.btn_calculator_two.setOnClickListener {
            RxBus.post(OnNumberButtonPressedBusObserver.OnNumberButtonPressed(2))
        }
        activity.btn_calculator_three.setOnClickListener {
            RxBus.post(OnNumberButtonPressedBusObserver.OnNumberButtonPressed(3))
        }
        activity.btn_calculator_four.setOnClickListener {
            RxBus.post(OnNumberButtonPressedBusObserver.OnNumberButtonPressed(4))
        }
        activity.btn_calculator_five.setOnClickListener {
            RxBus.post(OnNumberButtonPressedBusObserver.OnNumberButtonPressed(5))
        }
        activity.btn_calculator_six.setOnClickListener {
            RxBus.post(OnNumberButtonPressedBusObserver.OnNumberButtonPressed(6))
        }
        activity.btn_calculator_seven.setOnClickListener {
            RxBus.post(OnNumberButtonPressedBusObserver.OnNumberButtonPressed(7))
        }
        activity.btn_calculator_eight.setOnClickListener {
            RxBus.post(OnNumberButtonPressedBusObserver.OnNumberButtonPressed(8))
        }
        activity.btn_calculator_nine.setOnClickListener {
            RxBus.post(OnNumberButtonPressedBusObserver.OnNumberButtonPressed(9))
        }
        activity.btn_calculator_zero.setOnClickListener {
            RxBus.post(OnNumberButtonPressedBusObserver.OnNumberButtonPressed(0))
        }

        // Operator click listeners
        activity.btn_calculator_add.setOnClickListener {
            RxBus.post(OnOperatorButtonBusObserver.OnOperatorButtonPressed("+", getDisplayText()))
        }
        activity.btn_calculator_subtract.setOnClickListener {
            RxBus.post(OnOperatorButtonBusObserver.OnOperatorButtonPressed("-", getDisplayText()))
        }
        activity.btn_calculator_multiply.setOnClickListener {
            RxBus.post(OnOperatorButtonBusObserver.OnOperatorButtonPressed("*", getDisplayText()))
        }
        activity.btn_calculator_divide.setOnClickListener {
            RxBus.post(OnOperatorButtonBusObserver.OnOperatorButtonPressed("/", getDisplayText()))
        }

        // Equals click listener
        activity.btn_calculator_equals.setOnClickListener {
            RxBus.post(OnEqualsButtonPressedBusObserver.OnEqualsButtonPressed(getDisplayText()))
        }
    }

    fun getDisplayText(): String {
        return activity!!.text_calculator_display.text.toString()
    }

    fun setDisplayText(text: String) {
        activity!!.text_calculator_display.text = text
    }

    fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
