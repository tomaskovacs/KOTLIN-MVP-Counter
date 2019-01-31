package com.globant.counter.rx.events

import com.globant.counter.utils.BusObserver

abstract class OnClearButtonPressedBusObserver : BusObserver<OnClearButtonPressedBusObserver.OnClearButtonPressed>
(OnClearButtonPressedBusObserver.OnClearButtonPressed::class.java) {

    class OnClearButtonPressed
}
