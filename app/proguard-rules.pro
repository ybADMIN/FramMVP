# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in F:\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
  -dontwarn okio.**
  -dontwarn javax.annotation.**
  -dontwarn javax.annotation.Nullable
  -dontwarn javax.annotation.ParametersAreNonnullByDefault
  -keepattributes Signature
  -dontwarn retrofit2.Platform$Java8
  -dontwarn retrofit.**
  -keep class retrofit.** { *; }
#删除文件日志
-printusage unused.txt
#混淆前后映射
-printmapping mapping.txt
-dontwarn android.support.multidex.**
-keep class android.support.multidex.**{*;}
  -keep class android.support.v4.** { *; }
  -keep interface android.support.v4.** { *; }
  # Keep the support library
  -keep class android.support.v7.** { *; }
  -keep interface android.support.v7.** { *; }
  -keep interface android.support.design.** { *; }
#  -keep class android.support.design.** { *; }

  #【保护指定的类文件和类的成员】
  -keep class * implements android.os.Parcelable {
      public *;
  }
  -keepclassmembers class * implements android.os.Parcelable {
       private void writeObject(java.io.ObjectOutputStream);
       private void readObject(java.io.ObjectInputStream);
       java.lang.Object writeReplace();
       java.lang.Object readResolve();
  }
  -keepclassmembers class **.R$* {
      public static <fields>;
  }
 -keepnames class * implements java.io.Serializable
 -keep public class * implements java.io.Serializable {
    public *;
 }
 -dontwarn java.lang.invoke.*