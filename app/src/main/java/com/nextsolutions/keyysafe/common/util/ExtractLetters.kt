package com.nextsolutions.keyysafe.common.util

object ExtractLetters {
    fun extractFirstTwoLetters(input: String): String {
        val words = input.split(" ")
        val firstTwoLetters = StringBuilder()

        for (word in words) {
            if (firstTwoLetters.length >= 2) {
                break // Stop after extracting the first two letters
            }

            if (word.length >= 2) {
                firstTwoLetters.append(word.substring(0, 2))
            }
        }

        return firstTwoLetters.toString()
    }
}