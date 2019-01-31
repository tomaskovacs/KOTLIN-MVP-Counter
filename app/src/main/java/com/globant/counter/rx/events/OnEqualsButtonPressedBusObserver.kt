package com.globant.counter.rx.events

import com.globant.counter.utils.BusObserver

abstract class OnEqualsButtonPressedBusObserver : BusObserver<OnEqualsButtonPressedBusObserver.OnEqualsButtonPressed>
(OnEqualsButtonPressedBusObserver.OnEqualsButtonPressed::class.java){

    class OnEqualsButtonPressed(val displayValue: String)
}