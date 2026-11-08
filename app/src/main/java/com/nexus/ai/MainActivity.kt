package com.nexus.ai

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nexus.ai.ui.chat.ChatScreen
import com.nexus.ai.ui.drawer.ChatDrawer
import com.nexus.ai.ui.settings.SettingsScreen
import com.nexus.ai.ui.theme.NexusTheme
import com.nexus.ai.vm.ChatViewModel
import com.nexus.ai.vm.DrawerViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NexusTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    NexusRoot()
                }
            }
        }
    }
}

@Composable
fun NexusRoot() {
    val chatViewModel: ChatViewModel = viewModel()
    val drawerViewModel: DrawerViewModel = viewModel()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var currentScreen by remember { mutableStateOf("chat") }
    var webView by remember { mutableStateOf<WebView?>(null) }

    LaunchedEffect(Unit) { if (chatViewModel.currentChatId == null) chatViewModel.createNewChat() }

    Box(modifier = Modifier.fillMaxSize()) {
        if (currentScreen == "settings") {
            SettingsScreen(onBack = { currentScreen = "chat" }, onClearChats = { chatViewModel.clearChats() })
        } else {
            ChatDrawer(
                drawerState = drawerState,
                viewModel = drawerViewModel,
                chatViewModel = chatViewModel,
                onSettingsClick = { scope.launch { drawerState.close() }; currentScreen = "settings" }
            ) {
                ChatScreen(viewModel = chatViewModel, onMenuClick = { scope.launch { drawerState.open() } })
            }
        }

        // الـ WebView المخفي (1dp) الذي يدير Puter
        AndroidView(factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                webViewClient = WebViewClient()
                addJavascriptInterface(object {
                    @android.webkit.JavascriptInterface
                    fun onResponse(text: String) {
                        chatViewModel.addAiMessage(text)
                    }
                }, "AndroidBridge")
                loadUrl("file:///android_asset/puter_bridge.html")
                webView = this
            }
        }, modifier = Modifier.size(1.dp))
    }
}
