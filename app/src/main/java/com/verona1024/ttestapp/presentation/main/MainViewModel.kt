package com.verona1024.ttestapp.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.verona1024.ttestapp.domain.TimeHashUseCase
import com.verona1024.ttestapp.presentation.base.BaseViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.*
import kotlin.concurrent.schedule

class MainViewModel : BaseViewModel() {
    companion object {
        private const val START_DELAY = 0L
        private const val LOOP = 1000L
        private const val STEP = 1000L
        private const val COUNT = 4
    }

    private var mutableLiveData: MutableLiveData<List<Pair<String, String>>> = MutableLiveData()
    private lateinit var timerHashUseCase: TimeHashUseCase

    override fun init() {
        timerHashUseCase = TimeHashUseCase()
        Timer().schedule(START_DELAY, LOOP) {
            launch {
                try {
                    mutableLiveData.value = timerHashUseCase.execute(STEP, COUNT)
                } catch (e: IOException) {
                    // Could not get data
                }
            }
        }
    }

    fun getClockHash(): LiveData<List<Pair<String, String>>>? = mutableLiveData
}
