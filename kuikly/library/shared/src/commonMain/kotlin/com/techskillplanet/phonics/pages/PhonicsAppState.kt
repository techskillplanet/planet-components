package com.techskillplanet.phonics.pages

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.techskillplanet.phonics.data.PhonemeRepository
import com.techskillplanet.phonics.modules.PhonicsAudioModule
import com.techskillplanet.phonics.modules.PhonicsRecorderModule
import com.techskillplanet.phonics.modules.PhonicsStorageModule
import com.techskillplanet.phonics.modules.StorageKeys
import com.techskillplanet.phonics.theme.PhonicsTheme

class PhonicsAppState(
    private val storage: PhonicsStorageModule,
    val audio: PhonicsAudioModule,
    val recorder: PhonicsRecorderModule,
) {
    val themeKey = mutableStateOf(storage.getString(StorageKeys.Theme, PhonicsTheme.Sky.key))
    val language = mutableStateOf(storage.getString(StorageKeys.Language, "zh-CN"))
    val completed = mutableStateListOf<String>().apply {
        addAll(storage.getStringList(StorageKeys.Completed))
    }
    val selectedPhonemeId = mutableStateOf(PhonemeRepository.firstUnfinished(completed).id)

    val theme get() = PhonicsTheme.get(themeKey.value)

    fun selectPhoneme(id: String) {
        selectedPhonemeId.value = id
        audio.playPhoneme(id)
    }

    fun setTheme(key: String) {
        themeKey.value = key
        storage.putString(StorageKeys.Theme, key)
    }

    fun setLanguage(code: String) {
        language.value = code
        storage.putString(StorageKeys.Language, code)
    }

    fun complete(id: String) {
        if (id !in completed) {
            completed.add(id)
            storage.putStringList(StorageKeys.Completed, completed)
        }
    }
}
