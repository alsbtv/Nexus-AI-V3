package com.nexus.ai.data.repo
import android.content.Context
import com.nexus.ai.data.db.*
import kotlinx.coroutines.flow.Flow
class ChatRepository private constructor(private val db: NexusDatabase) {
    val chats: Flow<List<ChatEntity>> get() = db.chatDao().observeAll()
    val pins: Flow<List<PinnedMessageEntity>> get() = db.pinDao().observeAll()
    fun messages(chatId: Long): Flow<List<MessageEntity>> = db.messageDao().observeForChat(chatId)
    suspend fun createChat(title: String): Long = db.chatDao().insert(ChatEntity(title = title, timestamp = System.currentTimeMillis()))
    suspend fun getChat(id: Long): ChatEntity? = db.chatDao().getById(id)
    suspend fun renameChat(id: Long, title: String) = db.chatDao().rename(id, title)
    suspend fun setPinned(id: Long, pinned: Boolean) = db.chatDao().setPinned(id, pinned)
    suspend fun deleteChat(id: Long) { db.chatDao().deleteById(id); db.messageDao().deleteForChat(id) }
    suspend fun clearAllChats() = db.chatDao().clearAll()
    suspend fun addMessage(msg: MessageEntity): Long = db.messageDao().insert(msg)
    suspend fun getMessage(id: Long): MessageEntity? = db.messageDao().getById(id)
    suspend fun updateMessage(msg: MessageEntity) = db.messageDao().update(msg)
    suspend fun pinMessage(chatId: Long, messageId: Long, name: String) = db.pinDao().insert(PinnedMessageEntity(chatId = chatId, messageId = messageId, pinName = name, timestamp = System.currentTimeMillis()))
    companion object {
        @Volatile private var INSTANCE: ChatRepository? = null
        fun from(context: Context): ChatRepository = INSTANCE ?: synchronized(this) {
            INSTANCE ?: ChatRepository(NexusDatabase.get(context)).also { INSTANCE = it }
        }
    }
}
