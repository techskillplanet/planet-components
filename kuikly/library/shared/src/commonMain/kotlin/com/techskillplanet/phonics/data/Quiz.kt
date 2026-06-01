package com.techskillplanet.phonics.data

object QuizBuilder {
    fun listenOptions(target: Phoneme, seed: Int): List<String> {
        return options(
            correct = target.symbol,
            pool = PhonemeRepository.phonemes.map { it.symbol },
            seed = seed,
        )
    }

    fun wordOptions(target: Phoneme, seed: Int): Pair<List<String>, Int> {
        val correct = target.words.first().text
        val options = options(
            correct = correct,
            pool = PhonemeRepository.phonemes.flatMap { phoneme -> phoneme.words.map { it.text } },
            seed = seed,
        )
        return options to options.indexOf(correct)
    }

    private fun options(correct: String, pool: List<String>, seed: Int): List<String> {
        val result = mutableListOf(correct)
        var cursor = seed
        val candidates = pool.distinct().filter { it != correct }
        while (result.size < 4 && candidates.isNotEmpty()) {
            val candidate = candidates[kotlin.math.abs(cursor) % candidates.size]
            if (candidate !in result) result += candidate
            cursor = cursor * 31 + 7
        }
        return result.sortedBy { stableOrder(it, seed) }
    }

    private fun stableOrder(value: String, seed: Int): Int {
        return value.fold(seed) { acc, ch -> acc * 33 + ch.code }
    }
}
