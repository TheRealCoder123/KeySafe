package com.nextsolutions.keyysafe.settings.ui.Database.domain.repository

import android.net.Uri
import com.nextsolutions.keyysafe.settings.ui.Database.domain.data.BackupData
import com.nextsolutions.keyysafe.settings.ui.Database.domain.data.RestoreResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

interface RestoreRepository {
    suspend fun restore(
        fileUri: Uri,
        filePass: String
    ) : RestoreResponse
    fun decryptData2(encryptedData: ByteArray, encryptionKey: SecretKey) : String {
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.DECRYPT_MODE, encryptionKey)
        val decryptedData = cipher.doFinal(encryptedData)
        return String(decryptedData)
    }

    suspend fun decryptWithPassword(encryptedData: ByteArray, password: String): String {
        return withContext(Dispatchers.IO){
            val saltSize = 16
            val ivSize = 12
            val salt = encryptedData.sliceArray(0 until saltSize)
            val iv = encryptedData.sliceArray(saltSize until saltSize + ivSize)
            val encryptedBytes = encryptedData.sliceArray(saltSize + ivSize until encryptedData.size)

            // Derive the secret key from the password and salt using PBKDF2
            val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
            val spec = PBEKeySpec(password.toCharArray(), salt, 65536, 256)
            val tmp = factory.generateSecret(spec)
            val secretKey = SecretKeySpec(tmp.encoded, "AES")

            // Initialize the cipher with AES/GCM/NoPadding
            val cipher = Cipher.getInstance("AES/GCM/NoPadding")
            val gcmParameterSpec = GCMParameterSpec(128, iv)
            cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmParameterSpec)

            // Decrypt the data
            val decryptedData = cipher.doFinal(encryptedBytes)

            String(decryptedData)
        }

    }

    suspend fun saveDataInDatabase(backUpData: BackupData)
}