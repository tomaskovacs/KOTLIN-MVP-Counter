package com.globant.counter.mvp.view

import android.app.Activity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.globant.counter.rx.events.*
import com.globant.counter.utils.Constants
import com.globant.counter.utils.RxBus
import kotlinx.android.synthetic.main.activity_main.*

class CalculatorView(activity: Activity) : ActivityView(activity) {

    init {
        // Numbers click listener
        val btnNumberListener: (View) -> Unit = {
            RxBus.post(OnNumberButtonPressedBusObserver.OnNumberButtonPressed((it as TextView).text.toString().toLong()))
        }

        activity.btn_calculator_one.setOnClickListener(btnNumberListener)
        activity.btn_calculator_two.setOnClickListener(btnNumberListener)
        activity.btn_calculator_three.setOnClickListener(btnNumberListener)
        activity.btn_calculator_four.setOnClickListener(btnNumberListener)
        activity.btn_calculator_five.setOnClickListener(btnNumberListener)
        activity.btn_calculator_six.setOnClickListener(btnNumberListener)
        activity.btn_calculator_seven.setOnClickListener(btnNumberListener)
        activity.btn_calculator_eight.setOnClickListener(btnNumberListener)
        activity.btn_calculator_nine.setOnClickListener(btnNumberListener)
        activity.btn_calculator_zero.setOnClickListener(btnNumberListener)

        // Operator click listeners
        val btnOperatorListener: (View) -> Unit = {
            RxBus.post(OnOperatorButtonBusObserver.OnOperatorButtonPressed((it as TextView).text.toString(), getDisplayText()))
        }

        activity.btn_calculator_add.setOnClickListener(btnOperatorListener)
        activity.btn_calculator_subtract.setOnClickListener(btnOperatorListener)
        activity.btn_calculator_multiply.setOnClickListener(btnOperatorListener)
        activity.btn_calculator_divide.setOnClickListener(btnOperatorListener)

        // Equals click listener
        activity.btn_calculator_equals.setOnClickListener {
            RxBus.post(OnEqualsButtonPressedBusObserver.OnEqualsButtonPressed(getDisplayText()))
        }

        // Clear click listener
        activity.btn_calculator_clear.setOnClickListener {
            RxBus.post(OnClearButtonPressedBusObserver.OnClearButtonPressed())
        }

        // Dot click listener
        activity.btn_calculator_dot.setOnClickListener {
            RxBus.post(OnDotButtonPressedBusObserver.OnDotButtonPressed(getDisplayText()))
        }
    }

    fun getDisplayText(): String {
        return activity?.text_calculator_display?.text.toString()
    }

    fun setDisplayText(text: String) {
        activity?.text_calculator_display?.text = text
    }

    fun addNumber(number: String) {
        setDisplayText(getDisplayText() + number)
    }

    fun addOperator(operator: String) {
        setDisplayText(getDisplayText() + operator)
    }

    fun addDot() {
        setDisplayText(getDisplayText() + Constants.DOT)
    }

    fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
