package com.example.app7.prefs

import android.content.Context

class UserPreferencesImpl(
    context: Context
) : UserPreferences {

    private val preferencesManager by lazy {
        PreferencesManager(context = context, name = PREFERENCES_KEY)
    }

    override fun putLink(link: String?) {
        preferencesManager.putStringValue(LINK_FIREBASE, link)
    }

    override fun getLink(): String? {
        return preferencesManager.getStringValue(LINK_FIREBASE, "")
    }

    override fun clearAll() {
        preferencesManager.clearAll()
    }

    companion object {
        private const val LINK_FIREBASE = "link_firebase"
        private const val PREFERENCES_KEY = "my_pref"
    }
}