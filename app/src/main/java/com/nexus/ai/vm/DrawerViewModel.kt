package com.nexus.ai.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.nexus.ai.data.repo.ChatRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DrawerViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = ChatRepository.from(app)
    val chats = repo.chats.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    
    fun deleteChat(id: Long) { viewModelScope.launch { repo.deleteChat(id) } }
    fun renameChat(id: Long, title: String) { viewModelScope.launch { repo.renameChat(id, title) } }
}
