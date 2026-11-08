package com.nexus.ai.data.db
import androidx.room.*
import kotlinx.coroutines.flow.Flow
@Dao interface ChatDao {
    @Query("SELECT * FROM chats ORDER BY isPinned DESC, timestamp DESC") fun observeAll(): Flow<List<ChatEntity>>
    @Query("SELECT * FROM chats WHERE id = :id LIMIT 1") suspend fun getById(id: Long): ChatEntity?
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insert(chat: ChatEntity): Long
    @Query("UPDATE chats SET title = :title WHERE id = :id") suspend fun rename(id: Long, title: String)
    @Query("UPDATE chats SET isPinned = :pinned WHERE id = :id") suspend fun setPinned(id: Long, pinned: Boolean)
    @Query("DELETE FROM chats WHERE id = :id") suspend fun deleteById(id: Long)
    @Query("DELETE FROM chats") suspend fun clearAll()
}
@Dao interface MessageDao {
    @Query("SELECT * FROM messages WHERE chatId = :chatId ORDER BY timestamp ASC") fun observeForChat(chatId: Long): Flow<List<MessageEntity>>
    @Query("SELECT * FROM messages WHERE id = :id LIMIT 1") suspend fun getById(id: Long): MessageEntity?
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insert(message: MessageEntity): Long
    @Update suspend fun update(message: MessageEntity)
    @Query("DELETE FROM messages WHERE id = :id") suspend fun deleteById(id: Long)
    @Query("DELETE FROM messages WHERE chatId = :chatId") suspend fun deleteForChat(chatId: Long)
}
@Dao interface PinDao {
    @Query("SELECT * FROM pins ORDER BY timestamp DESC") fun observeAll(): Flow<List<PinnedMessageEntity>>
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insert(pin: PinnedMessageEntity): Long
    @Query("DELETE FROM pins WHERE id = :id") suspend fun deleteById(id: Long)
}
