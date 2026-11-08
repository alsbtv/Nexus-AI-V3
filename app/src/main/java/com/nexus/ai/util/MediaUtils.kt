package com.nexus.ai.util
import android.content.Context
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
object MediaUtils {
    suspend fun copyToCache(context: Context, uri: Uri, name: String): String? = withContext(Dispatchers.IO) {
        try {
            val dir = File(context.cacheDir, "nexus_import").apply { if (!exists()) mkdirs() }
            val out = File(dir, name)
            context.contentResolver.openInputStream(uri)?.use { input -> out.outputStream().use { output -> input.copyTo(output) } } ?: return@withContext null
            out.absolutePath
        } catch (t: Throwable) { null }
    }
}
