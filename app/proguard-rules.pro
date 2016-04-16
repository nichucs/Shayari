# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /home/nizamcs/Android/Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

-keepattributes InnerClasses,EnclosingMethod,Signature

# As described in tools/proguard/examples/android.pro - ignore all warnings.
-dontwarn android.support.v4.**
-dontwarn android.support.v7.**
-dontwarn javax.mail.**
-dontwarn javax.naming.Context
-dontwarn javax.naming.InitialContext
-dontwarn android.app.Application**
-dontwarn java.lang.String
-dontwarn com.google.android.gms.**

# Add any project specific keep options here:
-keep public class cs.nizam.shayari.model.** { *; }

#Butterknife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

#firebase
-keep class com.firebase.** { *; }
-keep class org.apache.** { *; }
-keepnames class com.fasterxml.jackson.** { *; }
-keepnames class javax.servlet.** { *; }
-keepnames class org.ietf.jgss.** { *; }
-dontwarn org.w3c.dom.**
-dontwarn org.joda.time.**
-dontwarn org.shaded.apache.**
-dontwarn org.ietf.jgss.**

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

 # Appcompat Material Design library
 -keep class android.support.v7.** { *; }
 -keep interface android.support.v7.** { *; }

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
