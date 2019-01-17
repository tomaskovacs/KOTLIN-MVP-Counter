package com.globant.counter

import com.globant.counter.mvp.model.CalculatorModel
import com.globant.counter.mvp.presenter.CalculatorPresenter
import com.globant.counter.mvp.view.CalculatorView
import com.globant.counter.rx.events.OnEqualsButtonPressedBusObserver
import com.globant.counter.rx.events.OnNumberButtonPressedBusObserver
import com.globant.counter.rx.events.OnOperatorButtonBusObserver
import com.globant.counter.utils.Constants
import com.globant.counter.utils.RxBus
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.`when` as whenever

class PresenterTest {

    private var presenter: CalculatorPresenter? = null
    @Mock
    lateinit var model: CalculatorModel
    @Mock
    lateinit var view: CalculatorView
    @Mock
    lateinit var activity: MainActivity

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        // When
        whenever(view.activity).thenReturn(activity)

        model = CalculatorModel()

        presenter = CalculatorPresenter(model, view)
        presenter!!.init()
    }

    @Test
    fun additionTest() {
        model.clearDisplayContent()
        RxBus.post(OnNumberButtonPressedBusObserver.OnNumberButtonPressed(1))
        RxBus.post(OnNumberButtonPressedBusObserver.OnNumberButtonPressed(1))
        RxBus.post(OnOperatorButtonBusObserver.OnOperatorButtonPressed(Constants.ADD_SYMBOL, "11"))
        RxBus.post(OnNumberButtonPressedBusObserver.OnNumberButtonPressed(2))
        RxBus.post(OnEqualsButtonPressedBusObserver.OnEqualsButtonPressed("11+2"))

        Assert.assertEquals(model.displayContent, "13.0")
    }

    @Test
    fun subtractionTest() {
        model.clearDisplayContent()
        RxBus.post(OnNumberButtonPressedBusObserver.OnNumberButtonPressed(3))
        RxBus.post(OnOperatorButtonBusObserver.OnOperatorButtonPressed(Constants.SUBTRACT_SYMBOL, "3"))
        RxBus.post(OnNumberButtonPressedBusObserver.OnNumberButtonPressed(9))
        RxBus.post(OnEqualsButtonPressedBusObserver.OnEqualsButtonPressed("3-9"))

        Assert.assertEquals(model.displayContent, "-6.0")
    }

    @Test
    fun multiplicationTest() {
        model.clearDisplayContent()
        RxBus.post(OnNumberButtonPressedBusObserver.OnNumberButtonPressed(1))
        RxBus.post(OnNumberButtonPressedBusObserver.OnNumberButtonPressed(1))
        RxBus.post(OnOperatorButtonBusObserver.OnOperatorButtonPressed(Constants.MULTIPLY_SYMBOL, "11"))
        RxBus.post(OnNumberButtonPressedBusObserver.OnNumberButtonPressed(2))
        RxBus.post(OnEqualsButtonPressedBusObserver.OnEqualsButtonPressed("11*2"))

        Assert.assertEquals(model.displayContent, "22.0")
    }

    @Test
    fun divisionTest() {
        model.clearDisplayContent()
        RxBus.post(OnNumberButtonPressedBusObserver.OnNumberButtonPressed(1))
        RxBus.post(OnNumberButtonPressedBusObserver.OnNumberButtonPressed(1))
        RxBus.post(OnOperatorButtonBusObserver.OnOperatorButtonPressed(Constants.DIVIDE_SYMBOL, "11"))
        RxBus.post(OnNumberButtonPressedBusObserver.OnNumberButtonPressed(2))
        RxBus.post(OnEqualsButtonPressedBusObserver.OnEqualsButtonPressed("11/2"))

        Assert.assertEquals(model.displayContent, "5.5")
    }
}