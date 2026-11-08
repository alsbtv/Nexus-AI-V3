package com.nexus.ai.system

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class GitHubUpdateManager {
    private val client = OkHttpClient()

    suspend fun checkLatestRelease(repo: String): JSONObject? = withContext(Dispatchers.IO) {
        try {
            val request = Request.Builder().url("https://api.github.com/repos/$repo/releases/latest").build()
            val response = client.newCall(request).execute()
            if (response.isSuccessful) JSONObject(response.body?.string() ?: "{}") else null
        } catch (e: Exception) {
            null
        }
    }
}
