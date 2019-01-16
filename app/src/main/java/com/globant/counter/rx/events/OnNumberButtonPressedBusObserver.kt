package com.globant.counter.rx.events

import com.globant.counter.utils.BusObserver

abstract class OnNumberButtonPressedBusObserver : BusObserver<OnNumberButtonPressedBusObserver.OnNumberButtonPressed>
(OnNumberButtonPressedBusObserver.OnNumberButtonPressed::class.java) {

    class OnNumberButtonPressed(val number: Number)
}