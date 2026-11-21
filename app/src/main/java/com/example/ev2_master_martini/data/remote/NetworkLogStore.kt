package com.example.ev2_master_martini.data.remote

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class NetworkCall(
    val method: String,
    val url: String,
    val code: Int,
    val durationMs: Long,
    val timestamp: Long
)

object NetworkLogStore {

    private const val MAX_CALLS = 50

    private val _calls = MutableStateFlow<List<NetworkCall>>(emptyList())
    val calls = _calls.asStateFlow()

    fun add(call: NetworkCall) {
        _calls.update { current ->
            (listOf(call) + current).take(MAX_CALLS)
        }
    }

    fun clear() {
        _calls.value = emptyList()
    }
}
