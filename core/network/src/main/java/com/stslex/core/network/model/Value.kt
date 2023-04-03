package com.stslex.core.network.model

sealed interface Value<out T> {

    data class Content<out T>(val data: T) : Value<T>

    data class Error(val error: Throwable) : Value<Nothing>

    object Loading : Value<Nothing>
}