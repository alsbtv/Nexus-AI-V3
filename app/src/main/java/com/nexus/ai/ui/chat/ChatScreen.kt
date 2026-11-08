package com.nexus.ai.ui.chat
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nexus.ai.vm.ChatViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(viewModel: ChatViewModel, onMenuClick: () -> Unit) {
    var inputText by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val currentChatId = viewModel.currentChatId
    
    val messages by if (currentChatId != null) {
        viewModel.getMessages(currentChatId).collectAsStateWithLifecycle(initialValue = emptyList())
    } else {
        remember { mutableStateOf(emptyList()) }
    }

    LaunchedEffect(messages.size) { if (messages.isNotEmpty()) listState.animateScrollToItem(messages.size - 1) }

    Scaffold(
        topBar = { NexusTopBar(chatTitle = "Nexus AI", modelName = viewModel.currentModel, onMenuClick = onMenuClick, onModelClick = {}, onNewChatClick = { viewModel.createNewChat() }, onMontageClick = {}) },
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            NexusInputBar(
                text = inputText, onTextChange = { inputText = it },
                onSend = {
                    if (inputText.isNotBlank()) {
                        viewModel.sendMessage(inputText)
                        inputText = ""
                    }
                },
                onAttachClick = {}
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            if (messages.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("كيف أساعدك اليوم؟", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            } else {
                LazyColumn(state = listState, modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(messages) { msg -> MessageRow(msg) }
                }
            }
        }
    }
}
