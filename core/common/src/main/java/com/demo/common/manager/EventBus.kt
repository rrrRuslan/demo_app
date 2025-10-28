package com.demo.common.manager

import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterIsInstance
import kotlin.coroutines.coroutineContext

sealed class EventBusEvent

data object LogoutEvent : EventBusEvent()
data object RefreshMainScreen : EventBusEvent()
data class ErrorEvent(val message: String) : EventBusEvent()
data class NavigationDeepLink(val screenRoute: String): EventBusEvent()

object EventBus {
    private val _events = MutableSharedFlow<EventBusEvent>()
    val events = _events.asSharedFlow()

    suspend fun publish(event: EventBusEvent) {
        _events.emit(event)
    }

    suspend inline fun <reified T : EventBusEvent> subscribe(crossinline onEvent: (T) -> Unit) {
        events.filterIsInstance<T>()
            .collectLatest { event ->
                coroutineContext.ensureActive()
                onEvent(event)
            }
    }

    suspend inline fun onEachEvent(crossinline onEvent: suspend (EventBusEvent) -> Unit) {
        events.collectLatest { event ->
            coroutineContext.ensureActive()
            onEvent(event)
        }
    }
}

