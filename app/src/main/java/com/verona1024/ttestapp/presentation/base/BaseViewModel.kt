package com.verona1024.ttestapp.presentation.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), CoroutineScope {
    private var mJob: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = mJob + Dispatchers.Main

    abstract fun init()
}