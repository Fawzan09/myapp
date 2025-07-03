# 🚀 Quick Start Guide - How to Run Phi-1 Android App

## Prerequisites

### Required Software
- **Android Studio** (Arctic Fox 2020.3.1 or newer)
- **JDK 8+** (usually included with Android Studio)
- **Android SDK** with API level 24-34

### Device Options
- **Physical Android Device** (Android 7.0+, API 24+)
- **Android Emulator** (created through Android Studio)

## Step-by-Step Instructions

### 1. Setup Development Environment

```bash
# Download and install Android Studio from:
# https://developer.android.com/studio

# During installation, make sure to include:
# - Android SDK
# - Android Virtual Device (AVD)
# - Performance optimization (Intel HAXM)
```

## Alternative: VS Code Development

### VS Code Setup for Android Development

#### 1. Install Required Extensions
```bash
# Install these VS Code extensions:
# - Android iOS Emulator (by DiemasMichiels)
# - Kotlin Language (by fwcd)
# - Gradle for Java (by Microsoft)
# - Android (by adelphes)
# - Java Extension Pack (by Microsoft)
```

#### 2. Install Android SDK
```bash
# Option A: Install Android Studio first (recommended)
# This automatically sets up Android SDK
# Then set ANDROID_HOME environment variable

# Option B: SDK Tools only (command line)
# Download SDK tools from:
# https://developer.android.com/studio#command-tools

export ANDROID_HOME=/path/to/android-sdk
export PATH=$PATH:$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools
```

#### 3. VS Code Workspace Configuration
Create `.vscode/settings.json`:
```json
{
    "java.configuration.updateBuildConfiguration": "automatic",
    "java.gradle.wrapper.enabled": true,
    "android.sdk.path": "/path/to/android-sdk",
    "kotlin.compiler.jvm.target": "1.8"
}
```

#### 4. Running in VS Code

**Method A: Integrated Terminal**
```bash
# Open terminal in VS Code (Ctrl+`)
./gradlew installDebug

# Or use the build script
./build.sh
```

**Method B: VS Code Tasks**
- Press `Ctrl+Shift+P`
- Type "Tasks: Run Task"
- Select "gradle: installDebug"

**Method C: Android Extension**
- Install Android extension
- Use Command Palette: "Android: Build APK"
- Use Command Palette: "Android: Install APK"
```

### 2. Open the Project

```bash
# Method A: From Android Studio
# File → Open → Navigate to /workspaces/myapp
# Click "Open"

# Method B: From command line
# cd /workspaces/myapp
# studio .  # If you have Android Studio in PATH
```

### 3. Sync and Build

```bash
# Android Studio will prompt:
# "Gradle files have changed since last project sync"
# Click "Sync Now"

# Wait for:
# ✅ Gradle sync to complete
# ✅ Dependencies to download
# ✅ Build tools to initialize
```

### 4. Setup Device/Emulator

#### Option A: Physical Device
```bash
# 1. Enable Developer Options on your Android device:
#    Settings → About Phone → Tap "Build Number" 7 times
#
# 2. Enable USB Debugging:
#    Settings → Developer Options → USB Debugging
#
# 3. Connect device via USB
# 4. Allow USB debugging when prompted
```

#### Option B: Android Emulator
```bash
# 1. In Android Studio: Tools → AVD Manager
# 2. Click "Create Virtual Device"
# 3. Choose device (e.g., Pixel 4)
# 4. Select system image (API 24+ recommended)
# 5. Click "Finish" and start emulator
```

### 5. Run the App

#### Method A: Android Studio GUI
```bash
# 1. Click the green "Run" button (▶️)
# 2. Select your target device
# 3. Click "OK"
```

#### Method B: Command Line
```bash
# Build and install debug version
./gradlew installDebug

# Or use the interactive script
./build.sh
# Choose option 3: "Install debug to connected device"
```

## What You'll See

### App Launch Sequence
1. **Splash Screen** with Phi-1 logo and gradient
2. **Security Check** - "🔄 Initializing AI model..."
3. **Biometric Prompt** (if device supports it)
4. **Main Screen** with "Start Conversation" button

### Main Features to Test
- ✅ **Chat Interface** - Send messages, get AI responses
- ✅ **Settings** - Toggle dark mode, security options
- ✅ **Biometric Auth** - Use fingerprint/face if available
- ✅ **Material Design** - Beautiful UI with animations

## Troubleshooting

### Common Issues

**"Gradle sync failed"**
```bash
# Solution: Update Gradle version
# File → Project Structure → Project → Gradle Version
```

**"Device not detected"**
```bash
# Solution: Check USB debugging is enabled
# Try different USB cable/port
# Restart adb: adb kill-server && adb start-server
```

**"App won't install"**
```bash
# Solution: Uninstall previous version
adb uninstall com.phi1app
./gradlew installDebug
```

**"Build fails"**
```bash
# Solution: Clean and rebuild
./gradlew clean
./gradlew assembleDebug
```

## Adding Your Phi-1 Model

### Current State
- App runs in **demo mode** with simulated responses
- All UI and security features work perfectly
- No actual model file required for testing

### Adding Real Model
```bash
# 1. Convert your Phi-1 model to TensorFlow Lite format
# 2. Copy to: app/src/main/assets/phi1_model.tflite
# 3. Rebuild and reinstall app
# 4. App automatically detects and uses real model
```

## Performance Tips

### For Development
- Use debug builds for faster development cycles
- Enable "Instant Run" in Android Studio
- Use physical device for better performance testing

### For Production
- Build release APK with ProGuard enabled
- Test on multiple device types and Android versions
- Profile memory usage and battery consumption

## Next Steps

### Immediate Testing
1. Launch the app and verify all screens work
2. Test biometric authentication (if available)
3. Try the chat interface with demo responses
4. Check settings and theme switching

### Production Preparation
1. Add your trained Phi-1 model
2. Test with real model inference
3. Generate signed release APK
4. Conduct security audit
5. Prepare for app store submission

---

## 🎯 Summary

The Phi-1 Android app is **ready to run** with:
- ✅ Complete Android project structure
- ✅ Modern Material Design 3 UI
- ✅ Enterprise-grade security features
- ✅ TensorFlow Lite integration
- ✅ Comprehensive documentation

Simply open in Android Studio and click Run! 🚀
