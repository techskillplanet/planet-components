package com.techskillplanet.phonics.modules

data class RecordingResult(
    val path: String,
    val durationMs: Long,
)

interface PhonicsRecorderModule {
    fun start(maxDurationMs: Long = 10_000)
    fun stop(onResult: (RecordingResult?) -> Unit)
    fun cancel()
}
