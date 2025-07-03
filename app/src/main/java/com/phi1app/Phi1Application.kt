package com.phi1app

import android.app.Application
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.phi1app.ml.ModelManager
import com.phi1app.security.SecurityManager

class Phi1Application : Application() {
    
    lateinit var modelManager: ModelManager
        private set
    
    lateinit var securityManager: SecurityManager
        private set
    
    private lateinit var encryptedPreferences: EncryptedSharedPreferences
    
    override fun onCreate() {
        super.onCreate()
        
        // Initialize security components
        initializeSecurity()
        
        // Initialize ML model manager
        modelManager = ModelManager(this)
        
        // Initialize security manager
        securityManager = SecurityManager(this)
    }
    
    private fun initializeSecurity() {
        try {
            val masterKey = MasterKey.Builder(this)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()
            
            encryptedPreferences = EncryptedSharedPreferences.create(
                this,
                "secure_prefs",
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            ) as EncryptedSharedPreferences
            
        } catch (e: Exception) {
            // Fallback to regular SharedPreferences with warning
            android.util.Log.w("Security", "Failed to initialize encrypted preferences: ${e.message}")
        }
    }
    
    fun getEncryptedPreferences() = encryptedPreferences
}
