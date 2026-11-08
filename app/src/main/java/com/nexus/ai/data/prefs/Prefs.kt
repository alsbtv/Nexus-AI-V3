package com.nexus.ai.data.prefs
import android.content.Context
import android.content.SharedPreferences
class Prefs(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("nexus_prefs", Context.MODE_PRIVATE)
    var signedIn: Boolean get() = prefs.getBoolean("signed_in", false); set(value) = prefs.edit().putBoolean("signed_in", value).apply()
    var userName: String get() = prefs.getString("user_name", "") ?: ""; set(value) = prefs.edit().putString("user_name", value).apply()
    var selectedModel: String get() = prefs.getString("selected_model", "auto") ?: "auto"; set(value) = prefs.edit().putString("selected_model", value).apply()
    var thinkingLevel: String get() = prefs.getString("thinking_level", "medium") ?: "medium"; set(value) = prefs.edit().putString("thinking_level", value).apply()
    var skill: String get() = prefs.getString("skill", "general") ?: "general"; set(value) = prefs.edit().putString("skill", value).apply()
}
