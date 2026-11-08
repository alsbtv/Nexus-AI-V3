package com.nexus.ai.vm

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.nexus.ai.data.db.MessageEntity
import com.nexus.ai.data.puter.PuterEngine
import com.nexus.ai.data.repo.ChatRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ChatViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = ChatRepository.from(app)
    val engine = PuterEngine(app)
    
    var currentChatId by mutableStateOf<Long?>(null)
        private set
    var currentModel by mutableStateOf("Auto")
    var isStreaming by mutableStateOf(false)

    // دالة عامة لتغيير المحادثة الحالية
    fun selectChat(id: Long) {
        currentChatId = id
    }

    fun createNewChat() {
        viewModelScope.launch {
            val id = repo.createChat("محادثة جديدة")
            currentChatId = id
        }
    }

    fun getMessages(chatId: Long) = repo.messages(chatId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun sendMessage(text: String, attachmentUri: String? = null) {
        val chatId = currentChatId ?: return
        viewModelScope.launch {
            repo.addMessage(MessageEntity(chatId = chatId, text = text, isUser = true, attachmentUri = attachmentUri, timestamp = System.currentTimeMillis()))
            engine.ask(text, currentModel, "")
            isStreaming = true
        }
    }

    fun addAiMessage(text: String) {
        val chatId = currentChatId ?: return
        viewModelScope.launch {
            repo.addMessage(MessageEntity(chatId = chatId, text = text, isUser = false, timestamp = System.currentTimeMillis()))
            isStreaming = false
        }
    }

    fun clearChats() {
        viewModelScope.launch {
            repo.clearAllChats()
            createNewChat()
        }
    }
}
