package com.example.app7.prefs

interface UserPreferences {

    fun putLink(link: String?)
    fun getLink(): String?

    fun clearAll()
}