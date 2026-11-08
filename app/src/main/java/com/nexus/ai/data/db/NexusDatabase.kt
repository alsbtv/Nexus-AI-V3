package com.nexus.ai.data.db
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [ChatEntity::class, MessageEntity::class, PinnedMessageEntity::class], version = 1, exportSchema = false)
abstract class NexusDatabase : RoomDatabase() {
    abstract fun chatDao(): ChatDao
    abstract fun messageDao(): MessageDao
    abstract fun pinDao(): PinDao
    companion object {
        @Volatile private var INSTANCE: NexusDatabase? = null
        fun get(context: Context): NexusDatabase = INSTANCE ?: synchronized(this) {
            Room.databaseBuilder(context.applicationContext, NexusDatabase::class.java, "nexus_ai.db").fallbackToDestructiveMigration().build().also { INSTANCE = it }
        }
    }
}
