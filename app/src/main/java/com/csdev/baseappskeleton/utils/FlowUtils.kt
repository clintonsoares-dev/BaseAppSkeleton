package com.csdev.baseappskeleton.utils

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow

suspend fun <T> MutableStateFlow<T?>.set(value: T, idle: T? = null, delay: Long = 100) {
    this.value = value
    delay(delay)
    this.value = idle
}