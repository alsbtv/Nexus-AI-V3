package com.nexus.ai.data.db
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "chats") data class ChatEntity(@PrimaryKey(autoGenerate = true) val id: Long = 0L, val title: String, val timestamp: Long, val isPinned: Boolean = false)
@Entity(tableName = "messages") data class MessageEntity(@PrimaryKey(autoGenerate = true) val id: Long = 0L, val chatId: Long, val text: String, val isUser: Boolean, val attachmentUri: String? = null, val timestamp: Long, val editHistory: String? = null, val isImage: Boolean = false, val isVideo: Boolean = false, val videoUri: String? = null)
@Entity(tableName = "pins") data class PinnedMessageEntity(@PrimaryKey(autoGenerate = true) val id: Long = 0L, val chatId: Long, val messageId: Long, val pinName: String, val timestamp: Long)
