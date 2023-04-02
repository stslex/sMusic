package com.stslex.feature.home.ui

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stslex.core.network.clients.YoutubeClient
import com.stslex.core.network.data.model.YouTubePage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(
    private val homeClient: YoutubeClient
) : ViewModel() {

    val pageResult: StateFlow<YouTubePage>
        get() = homeClient.makeNextRequest()
            .flowOn(Dispatchers.IO)
            .stateIn(
                viewModelScope,
                SharingStarted.Lazily,
                YouTubePage()
            )

}

@Suppress("UNCHECKED_CAST")
@Composable
fun <T> persist(tag: String, initialValue: T): MutableState<T> {
    val context = LocalContext.current

    return remember {
        context.persistMap
            ?.getOrPut(tag) { mutableStateOf(initialValue) } as? MutableState<T>
            ?: mutableStateOf(initialValue)
    }
}

@Composable
fun <T> persistList(tag: String): MutableState<List<T>> =
    persist(tag = tag, initialValue = emptyList())

@Composable
fun <T : Any?> persist(tag: String): MutableState<T?> =
    persist(tag = tag, initialValue = null)

val Context.persistMap: HashMap<String, Any?>?
    get() = findOwner<PersistMapOwner>()?.persistMap

internal inline fun <reified T> Context.findOwner(): T? {
    var context = this
    while (context is ContextWrapper) {
        if (context is T) return context
        context = context.baseContext
    }
    return null
}

interface PersistMapOwner {
    val persistMap: HashMap<String, Any?>
}

@Composable
fun PersistMapCleanup(tagPrefix: String) {
    val context = LocalContext.current
    DisposableEffect(context) {
        onDispose {
            if (context.findOwner<Activity>()?.isChangingConfigurations == false) {
                context.persistMap?.keys?.removeAll { it.startsWith(tagPrefix) }
            }
        }
    }
}