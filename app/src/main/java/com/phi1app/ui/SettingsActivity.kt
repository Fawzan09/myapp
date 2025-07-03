package com.phi1app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.phi1app.Phi1Application
import com.phi1app.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var app: Phi1Application
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        app = application as Phi1Application
        
        setupUI()
        loadSettings()
    }
    
    private fun setupUI() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Settings"
        
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        
        // Dark mode toggle
        binding.darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            saveBooleanPreference("dark_mode", isChecked)
        }
        
        // Biometric authentication toggle
        binding.biometricSwitch.setOnCheckedChangeListener { _, isChecked ->
            saveBooleanPreference("biometric_enabled", isChecked)
        }
        
        // Security features
        binding.securityInfoCard.setOnClickListener {
            showSecurityInfo()
        }
        
        // Model info
        updateModelInfo()
    }
    
    private fun loadSettings() {
        val prefs = app.getEncryptedPreferences()
        
        binding.darkModeSwitch.isChecked = prefs.getBoolean("dark_mode", false)
        binding.biometricSwitch.isChecked = prefs.getBoolean("biometric_enabled", true)
        binding.biometricSwitch.isEnabled = app.securityManager.isBiometricAvailable()
        
        if (!app.securityManager.isBiometricAvailable()) {
            binding.biometricDescription.text = "Biometric authentication not available on this device"
        }
    }
    
    private fun saveBooleanPreference(key: String, value: Boolean) {
        val prefs = app.getEncryptedPreferences()
        prefs.edit().putBoolean(key, value).apply()
    }
    
    private fun showSecurityInfo() {
        val securityFeatures = listOf(
            "✅ Local AI processing - your data never leaves your device",
            "✅ Encrypted storage for sensitive settings",
            "✅ Biometric authentication support",
            "✅ App integrity validation",
            "✅ Secure communication protocols",
            "✅ No data collection or telemetry"
        )
        
        val message = "Security Features:\n\n" + securityFeatures.joinToString("\n")
        
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Security Information")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }
    
    private fun updateModelInfo() {
        binding.modelStatusText.text = if (app.modelManager.isReady()) {
            "✅ Phi-1 model loaded and ready"
        } else {
            "🔄 Loading Phi-1 model..."
        }
        
        binding.modelDescriptionText.text = """
            Phi-1 is a compact language model designed for efficient on-device inference. 
            Key features:
            • Runs entirely offline
            • Optimized for mobile devices
            • Low memory footprint
            • Fast response times
        """.trimIndent()
    }
}
