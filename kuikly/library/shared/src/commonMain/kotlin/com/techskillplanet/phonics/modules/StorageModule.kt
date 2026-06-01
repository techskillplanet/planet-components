package com.techskillplanet.phonics.modules

interface PhonicsStorageModule {
    fun getString(key: String, defaultValue: String = ""): String
    fun putString(key: String, value: String)
    fun getStringList(key: String): List<String>
    fun putStringList(key: String, value: List<String>)
}

object StorageKeys {
    const val Theme = "theme"
    const val Language = "language"
    const val Completed = "completed"
}
