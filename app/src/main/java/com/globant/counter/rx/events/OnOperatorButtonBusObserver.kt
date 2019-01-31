package com.globant.counter.rx.events

import com.globant.counter.utils.BusObserver

abstract class OnOperatorButtonBusObserver : BusObserver<OnOperatorButtonBusObserver.OnOperatorButtonPressed>
(OnOperatorButtonBusObserver.OnOperatorButtonPressed::class.java){

    class OnOperatorButtonPressed(val mathOperator: String, val displayContent: String)
}