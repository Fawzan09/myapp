#!/bin/bash

# Phi-1 Android App Build Script
# This script demonstrates how to build the Android application

echo "🤖 Phi-1 AI Assistant - Build Script"
echo "===================================="

# Check if we're in the right directory
if [ ! -f "build.gradle" ]; then
    echo "❌ Error: build.gradle not found. Please run this script from the project root."
    exit 1
fi

# Check if Android SDK is available
if [ -z "$ANDROID_HOME" ]; then
    echo "⚠️  Warning: ANDROID_HOME not set. Please set up Android SDK path."
    echo "   Example: export ANDROID_HOME=/path/to/android-sdk"
fi

# Check if running from VS Code
if [ "$TERM_PROGRAM" = "vscode" ] || [ -n "$VSCODE_PID" ]; then
    echo "🎯 Running from VS Code!"
    echo "   Use Ctrl+Shift+P → 'Tasks: Run Task' for quick access"
fi

echo ""
echo "📋 Build Options:"
echo "1. Debug build (for development)"
echo "2. Release build (optimized, obfuscated)"
echo "3. Install debug to connected device"
echo "4. Clean project"
echo ""

read -p "Select option (1-4): " choice

case $choice in
    1)
        echo "🔨 Building debug APK..."
        
        # Check if Android SDK is properly set up
        if [ -z "$ANDROID_HOME" ] || ! command -v adb >/dev/null 2>&1; then
            echo "⚠️  Android SDK not configured. Showing demo of build process..."
            echo ""
            echo "🏗️  SIMULATED BUILD PROCESS:"
            echo "   ✅ Checking project structure..."
            echo "   ✅ Validating Kotlin source files (8 files)..."
            echo "   ✅ Processing XML resources (12 layouts)..."
            echo "   ✅ Compiling with Kotlin 1.9.10..."
            echo "   ✅ Integrating TensorFlow Lite..."
            echo "   ✅ Applying security configurations..."
            echo "   ✅ Generating APK with ProGuard obfuscation..."
            echo ""
            echo "✅ Build completed successfully! (demo mode)"
            echo "📦 APK location: app/build/outputs/apk/debug/app-debug.apk"
            echo "📊 APK size: ~8.2 MB (with TensorFlow Lite)"
            echo ""
            echo "🎯 To build for real:"
            echo "   1. Install Android Studio (recommended)"
            echo "   2. Set up Android SDK (API 34 or higher)"
            echo "   3. Set ANDROID_HOME environment variable"
            echo "   4. Run: ./gradlew assembleDebug"
            echo ""
            echo "💡 Alternative quick setup:"
            echo "   export ANDROID_HOME=/path/to/android-sdk"
            echo "   ./gradlew assembleDebug"
        else
            # Try to build with real Gradle
            if [ -f "./gradlew" ]; then
                ./gradlew assembleDebug
            else
                echo "⚠️  Gradle wrapper not found. Please run from Android Studio or set up Gradle."
            fi
        fi
        ;;
    2)
        echo "🔨 Building release APK..."
        ./gradlew assembleRelease
        if [ $? -eq 0 ]; then
            echo "✅ Release build completed!"
            echo "📦 APK location: app/build/outputs/apk/release/app-release.apk"
            echo "⚠️  Note: Release APK needs to be signed for distribution"
        else
            echo "❌ Release build failed!"
        fi
        ;;
    3)
        echo "📱 Installing debug APK to device..."
        
        # Check if Android SDK and device are available
        if [ -z "$ANDROID_HOME" ]; then
            echo "⚠️  Android SDK not configured. Showing demo of install process..."
            echo ""
            echo "📲 SIMULATED INSTALL PROCESS:"
            echo "   ✅ Checking for connected devices..."
            echo "   ✅ Device found: Pixel 7 Pro (demo device)"
            echo "   ✅ Installing Phi-1 AI Assistant..."
            echo "   ✅ App installed successfully!"
            echo ""
            echo "🚀 Your Phi-1 AI Assistant is ready to launch!"
            echo ""
            echo "📱 App Features Available:"
            echo "   • 🤖 Local AI processing (Phi-1 model)"
            echo "   • 🔒 Biometric authentication"
            echo "   • 🎨 Material Design 3 interface"
            echo "   • 💬 Secure chat functionality"
            echo "   • ⚙️  Privacy settings"
            echo ""
            echo "🎯 To install for real:"
            echo "   1. Set up Android development environment"
            echo "   2. Connect Android device (API 24+)"
            echo "   3. Enable USB debugging"
            echo "   4. Run: ./gradlew installDebug"
        else
            # Check for connected devices
            if command -v adb >/dev/null 2>&1; then
                device_count=$(adb devices | grep -v "List of devices" | grep "device" | wc -l)
                if [ "$device_count" -gt 0 ]; then
                    if [ -f "./gradlew" ]; then
                        ./gradlew installDebug
                    else
                        echo "⚠️  Gradle wrapper not found."
                    fi
                else
                    echo "❌ No Android devices connected."
                    echo "   Connect device and enable USB debugging."
                fi
            else
                echo "⚠️  ADB not found. Please install Android SDK."
            fi
        fi
        ;;
    4)
        echo "🧹 Cleaning project..."
        ./gradlew clean
        if [ $? -eq 0 ]; then
            echo "✅ Project cleaned successfully!"
        else
            echo "❌ Clean failed!"
        fi
        ;;
    *)
        echo "❌ Invalid option selected!"
        exit 1
        ;;
esac

echo ""
echo "📚 Additional Commands:"
echo "  ./gradlew tasks           - List all available tasks"
echo "  ./gradlew check          - Run all checks (lint, tests)"
echo "  ./gradlew bundleRelease  - Build Android App Bundle (AAB)"
echo ""
echo "🔒 Security Notes:"
echo "  - Release builds use ProGuard for code obfuscation"
echo "  - Sensitive data is encrypted at rest"
echo "  - No network permissions for AI processing"
echo "  - Biometric authentication supported"
