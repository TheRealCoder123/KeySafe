package com.nextsolutions.keyysafe.common

class PasswordChecker {
    fun checkPasswordStrength(password: String): PasswordStrength {

        val minLength = 8
        var hasUppercase = false
        var hasLowercase = false
        var hasDigit = false
        var hasSpecialChar = false
        if (password.length < minLength) {
            return PasswordStrength.WEAK
        }
        for (ch in password) {
            if (ch.isUpperCase()) {
                hasUppercase = true
            } else if (ch.isLowerCase()) {
                hasLowercase = true
            } else if (ch.isDigit()) {
                hasDigit = true
            } else if (isSpecialCharacter(ch)) {
                hasSpecialChar = true
            }
        }

        return if (hasUppercase && hasLowercase && hasDigit && hasSpecialChar) {
            PasswordStrength.STRONG
        } else if ((hasUppercase || hasLowercase) && hasDigit) {
            PasswordStrength.MEDIUM
        } else {
            PasswordStrength.WEAK
        }
    }

    private fun isSpecialCharacter(ch: Char): Boolean {
        val specialCharacters = "!@#$%^&*()_+"
        return specialCharacters.contains(ch)
    }

    enum class PasswordStrength {
        STRONG, MEDIUM, WEAK
    }

}