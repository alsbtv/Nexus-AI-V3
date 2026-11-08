package com.nexus.ai.media

import android.content.Context
import java.io.File

class FFmpegHelper {
    fun combineVideoWithAudio(
        videoUrl: String,
        audioUrl: String,
        context: Context,
        onResult: (String?) -> Unit
    ) {
        val output = File(context.cacheDir, "combined.mp4")
        onResult(output.absolutePath)
    }
}
