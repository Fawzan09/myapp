package com.phi1app

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.phi1app.databinding.ActivityMainBinding
import com.phi1app.ui.ChatActivity
import com.phi1app.ui.SettingsActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private lateinit var app: Phi1Application
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        app = application as Phi1Application
        
        setupUI()
        checkSecurity()
    }
    
    private fun setupUI() {
        // Set up modern UI animations
        val fadeIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        binding.logoImageView.startAnimation(fadeIn)
        
        // Set up click listeners
        binding.startChatButton.setOnClickListener {
            startChatActivity()
        }
        
        binding.settingsButton.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        
        // Show model loading status
        updateModelStatus()
    }
    
    private fun checkSecurity() {
        if (!app.securityManager.validateAppIntegrity()) {
            binding.statusTextView.text = "⚠️ Security Warning: App integrity check failed"
            binding.statusTextView.setTextColor(getColor(android.R.color.holo_red_dark))
        }
    }
    
    private fun updateModelStatus() {
        lifecycleScope.launch {
            while (!app.modelManager.isReady()) {
                binding.statusTextView.text = "🔄 Loading AI model..."
                delay(1000)
            }
            binding.statusTextView.text = "✅ AI model ready"
            binding.statusTextView.setTextColor(getColor(android.R.color.holo_green_dark))
            binding.startChatButton.isEnabled = true
        }
    }
    
    private fun startChatActivity() {
        if (app.securityManager.isBiometricAvailable()) {
            app.securityManager.authenticateWithBiometric(
                activity = this,
                onSuccess = {
                    launchChat()
                },
                onError = { error ->
                    binding.statusTextView.text = "Authentication failed: $error"
                }
            )
        } else {
            launchChat()
        }
    }
    
    private fun launchChat() {
        val intent = Intent(this, ChatActivity::class.java)
        startActivity(intent)
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
    }
}
