package com.nextsolutions.keyysafe.common.password_manager

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

class PasswordEncrypt(
   private val encryptionKey: SecretKey
) {
    fun encryptPassword(password: String): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        cipher.init(Cipher.ENCRYPT_MODE, encryptionKey)

        val ivBytes = cipher.iv
        val encryptedBytes = cipher.doFinal(password.toByteArray(Charsets.UTF_8))

        // Combine IV and encrypted data into a single string
        val ivAndEncrypted = ByteArray(ivBytes.size + encryptedBytes.size)
        System.arraycopy(ivBytes, 0, ivAndEncrypted, 0, ivBytes.size)
        System.arraycopy(encryptedBytes, 0, ivAndEncrypted, ivBytes.size, encryptedBytes.size)

        return Base64.encodeToString(ivAndEncrypted, Base64.NO_WRAP)
    }

    fun decryptPassword(encryptedPassword: String): String {
        val encryptedBytes = Base64.decode(encryptedPassword, Base64.NO_WRAP)

        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        val ivBytes = ByteArray(16) // IV is the first 16 bytes of encrypted data
        val encryptedData = ByteArray(encryptedBytes.size - ivBytes.size)

        System.arraycopy(encryptedBytes, 0, ivBytes, 0, ivBytes.size)
        System.arraycopy(encryptedBytes, ivBytes.size, encryptedData, 0, encryptedData.size)

        val ivSpec = IvParameterSpec(ivBytes)
        cipher.init(Cipher.DECRYPT_MODE, encryptionKey, ivSpec)

        val decryptedBytes = cipher.doFinal(encryptedData)
        return String(decryptedBytes, Charsets.UTF_8)
    }

}