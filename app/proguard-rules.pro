# Security-focused ProGuard configuration for Phi-1 App

# Keep TensorFlow Lite classes
-keep class org.tensorflow.lite.** { *; }
-keep class org.tensorflow.lite.support.** { *; }

# Keep security-related classes
-keep class androidx.security.crypto.** { *; }
-keep class androidx.biometric.** { *; }

# Keep data classes
-keep class com.phi1app.ui.model.** { *; }
-keep class com.phi1app.security.EncryptedData { *; }

# Keep Application class
-keep class com.phi1app.Phi1Application { *; }

# Obfuscate but don't remove security methods
-keepclassmembers class com.phi1app.security.SecurityManager {
    public *;
}

# Remove logging in release builds
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
}

# General Android keep rules
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes Exceptions

# Keep view binding classes
-keep class com.phi1app.databinding.** { *; }

# Optimize and obfuscate
-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*
-optimizationpasses 5
-allowaccessmodification
-dontpreverify

# Security: Remove debug information
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable
