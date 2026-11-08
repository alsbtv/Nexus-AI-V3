package com.nexus.ai.data.remote

import okhttp3.OkHttpClient

object ApiClients {
    val client = OkHttpClient.Builder().build()
    const val JIKAN_BASE = "https://api.jikan.moe/v4"
    const val AUDIUS_BASE = "https://discoveryprovider.audius.co/v1"
}
