# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# signingConfig, minifyEnabled and proguardFiles signing config block.

-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile
-keepattributes *Annotation*
-keep class * {
    @java.lang.Deprecated *;
}
