package com.example.app7.prefs

import android.content.Context

internal class PreferencesManager(
    private val context: Context,
    private val name: String
) {

    fun putStringValue(key: String, value: String?) {
        val sharedPref =
            context.getSharedPreferences(name, Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString(key, value)
            apply()
        }
    }

    fun getStringValue(key: String, defaultValue: String?): String? {
        val sharedPreferences =
            context.getSharedPreferences(name, Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, defaultValue)
    }

    fun putBooleanValue(key: String, value: Boolean?) {
        val sharedPref =
            context.getSharedPreferences(name, Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            if (value != null) {
                putBoolean(key, value)
            }
            apply()
        }
    }

    fun getBooleanValue(key: String, defaultValue: Boolean): Boolean {
        val sharedPreferences =
            context.getSharedPreferences(name, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    fun clearAll() {
        val sharedPref =
            context.getSharedPreferences(name, Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            clear()
            apply()
        }
    }
}