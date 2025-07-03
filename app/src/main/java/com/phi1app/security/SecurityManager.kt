package com.phi1app.security

import android.content.Context
import android.util.Log
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import java.security.MessageDigest
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec
import android.util.Base64

class SecurityManager(private val context: Context) {
    
    companion object {
        private const val TAG = "SecurityManager"
        private const val ALGORITHM = "AES"
        private const val TRANSFORMATION = "AES/GCM/NoPadding"
        private const val GCM_IV_LENGTH = 12
        private const val GCM_TAG_LENGTH = 16
    }
    
    private val secureRandom = SecureRandom()
    
    fun isBiometricAvailable(): Boolean {
        val biometricManager = BiometricManager.from(context)
        return when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK)) {
            BiometricManager.BIOMETRIC_SUCCESS -> true
            else -> false
        }
    }
    
    fun authenticateWithBiometric(
        activity: FragmentActivity,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (!isBiometricAvailable()) {
            onError("Biometric authentication not available")
            return
        }
        
        val executor = ContextCompat.getMainExecutor(context)
        val biometricPrompt = BiometricPrompt(activity, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                onError("Authentication error: $errString")
            }
            
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                onSuccess()
            }
            
            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                onError("Authentication failed")
            }
        })
        
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Secure Access Required")
            .setSubtitle("Use your biometric credential to access the AI assistant")
            .setNegativeButtonText("Cancel")
            .build()
        
        biometricPrompt.authenticate(promptInfo)
    }
    
    fun encryptData(data: String): EncryptedData? {
        try {
            val key = generateSecretKey()
            val cipher = Cipher.getInstance(TRANSFORMATION)
            
            val iv = ByteArray(GCM_IV_LENGTH)
            secureRandom.nextBytes(iv)
            val gcmSpec = GCMParameterSpec(GCM_TAG_LENGTH * 8, iv)
            
            cipher.init(Cipher.ENCRYPT_MODE, key, gcmSpec)
            val encryptedBytes = cipher.doFinal(data.toByteArray())
            
            return EncryptedData(
                encryptedData = Base64.encodeToString(encryptedBytes, Base64.DEFAULT),
                iv = Base64.encodeToString(iv, Base64.DEFAULT),
                key = Base64.encodeToString(key.encoded, Base64.DEFAULT)
            )
        } catch (e: Exception) {
            Log.e(TAG, "Encryption failed: ${e.message}")
            return null
        }
    }
    
    fun decryptData(encryptedData: EncryptedData): String? {
        try {
            val keyBytes = Base64.decode(encryptedData.key, Base64.DEFAULT)
            val key = SecretKeySpec(keyBytes, ALGORITHM)
            
            val iv = Base64.decode(encryptedData.iv, Base64.DEFAULT)
            val cipher = Cipher.getInstance(TRANSFORMATION)
            val gcmSpec = GCMParameterSpec(GCM_TAG_LENGTH * 8, iv)
            
            cipher.init(Cipher.DECRYPT_MODE, key, gcmSpec)
            val decryptedBytes = cipher.doFinal(Base64.decode(encryptedData.encryptedData, Base64.DEFAULT))
            
            return String(decryptedBytes)
        } catch (e: Exception) {
            Log.e(TAG, "Decryption failed: ${e.message}")
            return null
        }
    }
    
    private fun generateSecretKey(): SecretKey {
        val keyGenerator = KeyGenerator.getInstance(ALGORITHM)
        keyGenerator.init(256)
        return keyGenerator.generateKey()
    }
    
    fun hashData(data: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(data.toByteArray())
        return Base64.encodeToString(hashBytes, Base64.DEFAULT)
    }
    
    fun isDebugMode(): Boolean {
        return android.os.Debug.isDebuggerConnected() || 
               (context.applicationInfo.flags and android.content.pm.ApplicationInfo.FLAG_DEBUGGABLE) != 0
    }
    
    fun validateAppIntegrity(): Boolean {
        // Check if app is running in a secure environment
        if (isDebugMode()) {
            Log.w(TAG, "App is running in debug mode")
            return false
        }
        
        // Additional integrity checks can be added here
        return true
    }
}

data class EncryptedData(
    val encryptedData: String,
    val iv: String,
    val key: String
)
