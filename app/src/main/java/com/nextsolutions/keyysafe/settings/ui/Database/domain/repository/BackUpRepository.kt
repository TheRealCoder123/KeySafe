package com.nextsolutions.keyysafe.settings.ui.Database.domain.repository

import com.nextsolutions.keyysafe.settings.ui.Database.domain.data.BackUpResponse
import com.nextsolutions.keyysafe.settings.ui.Database.domain.data.BackupData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

interface BackUpRepository {
    suspend fun export(
        fileName: String,
        filePass: String,
    ) : BackUpResponse

    suspend fun getBackUpData() : BackupData

     suspend fun encrypt(data: String, password: String): ByteArray {
         return withContext(Dispatchers.IO){
             val salt = ByteArray(16)
             val random = SecureRandom()
             random.nextBytes(salt)

             val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
             val spec = PBEKeySpec(password.toCharArray(), salt, 65536, 256)
             val tmp = factory.generateSecret(spec)
             val secretKey = SecretKeySpec(tmp.encoded, "AES")

             // Initialize the cipher with AES/GCM/NoPadding
             val cipher = Cipher.getInstance("AES/GCM/NoPadding")
             cipher.init(Cipher.ENCRYPT_MODE, secretKey)

             // Encrypt the data
             val iv = cipher.iv
             val encryptedData = cipher.doFinal(data.toByteArray())

             // Combine salt, IV, and encrypted data
             val combinedData = ByteArray(salt.size + iv.size + encryptedData.size)
             System.arraycopy(salt, 0, combinedData, 0, salt.size)
             System.arraycopy(iv, 0, combinedData, salt.size, iv.size)
             System.arraycopy(encryptedData, 0, combinedData, salt.size + iv.size, encryptedData.size)

             // Return the result as a Base64-encoded string
             combinedData
         }

    }



}