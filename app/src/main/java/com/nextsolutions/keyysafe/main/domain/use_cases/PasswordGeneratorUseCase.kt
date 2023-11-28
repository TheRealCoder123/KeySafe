package com.nextsolutions.keyysafe.main.domain.use_cases

import java.security.SecureRandom

class PasswordGeneratorUseCase {


    private val lowercaseChars = "abcdefghijkmnopqrstuvwxyz"
    private val capitalLetters = "ABCDEFGHJKLMNPQRSTUVWXYZ"
    private val digits = "23456789"
    private val symbols = "!@#$%^&*()_+-=[]{}|;:,.<>?/|\\"


    fun generate(
        length: Int,
        useCapitalLetters: Boolean,
        useDigits: Boolean,
        useSymbols: Boolean
    ) : String {

        val allowedCharacters = StringBuilder()
        val passwordBuilder = StringBuilder(length)
        allowedCharacters.append(lowercaseChars)

        if (useCapitalLetters) allowedCharacters.append(capitalLetters)
        if (useDigits) allowedCharacters.append(digits)
        if (useSymbols) allowedCharacters.append(symbols)

        val random = SecureRandom()

        for (i in 0 until length) {
            val randomIndex = random.nextInt(allowedCharacters.length)
            passwordBuilder.append(allowedCharacters[randomIndex])
        }

        return passwordBuilder.toString()
    }
}