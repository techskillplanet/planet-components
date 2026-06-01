package com.techskillplanet.phonics.modules

interface PhonicsAudioModule {
    fun playPhoneme(id: String)
    fun playWord(text: String)
    fun playFile(path: String)
}

fun phonemeAssetPath(id: String): String = "audio/phonemes/$id.mp3"

fun wordAssetPath(text: String): String = "audio/words/${text.lowercase()}.mp3"
