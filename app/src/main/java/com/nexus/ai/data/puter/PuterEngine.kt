package com.nexus.ai.data.puter

import android.content.Context
import android.webkit.JavascriptInterface
import android.webkit.WebView
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

class PuterEngine(private val context: Context) {
    sealed class BridgeEvent {
        data class Chunk(val text: String) : BridgeEvent()
        data class Image(val url: String) : BridgeEvent()
        data class Auth(val signedIn: Boolean, val user: String) : BridgeEvent()
    }

    private val _events = MutableSharedFlow<BridgeEvent>()
    val events: SharedFlow<BridgeEvent> = _events

    private val _signedIn = MutableStateFlow(false)
    val signedIn: StateFlow<Boolean> = _signedIn

    private var webView: WebView? = null

    fun attach(wv: WebView) { webView = wv }
    fun detach() { webView = null }

    fun ask(prompt: String, model: String?) {
        val safePrompt = prompt.replace("'", "\\'")
        val safeModel = model?.replace("'", "\\'") ?: "auto"
        webView?.evaluateJavascript("askPuter('$safePrompt', '$safeModel', null)", null)
    }

    fun generateImage(prompt: String) {
        val safePrompt = prompt.replace("'", "\\'")
        webView?.evaluateJavascript("generateImage('$safePrompt')", null)
    }

    inner class Bridge {
        @JavascriptInterface fun onResponse(text: String) { _events.tryEmit(BridgeEvent.Chunk(text)) }
        @JavascriptInterface fun onImageGenerated(url: String) { _events.tryEmit(BridgeEvent.Image(url)) }
    }
}
