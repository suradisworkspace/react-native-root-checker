package com.rootchecker

//Custom
import android.os.Build
import com.bootloadercheck.BootloaderCheck
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.module.annotations.ReactModule
import com.scottyab.rootbeer.RootBeer
import java.util.Locale

@ReactModule(name = RootCheckerModule.NAME)
class RootCheckerModule(reactContext: ReactApplicationContext) :
  NativeRootCheckerSpec(reactContext) {

  override fun getName(): String {
    return NAME
  }

  // Example method
  // See https://reactnative.dev/docs/native-modules-android

  override fun checkRootJail(): Boolean {
    val rootBeer = RootBeer(reactApplicationContext)
    val isOnEmulator = (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
      || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
      || Build.BOARD.lowercase(Locale.ROOT).contains("nox")
      || Build.BOOTLOADER.lowercase(Locale.ROOT).contains("nox")
      || Build.FINGERPRINT.startsWith("generic")
      || Build.FINGERPRINT.startsWith("unknown")
      || Build.HARDWARE.contains("goldfish")
      || Build.HARDWARE.contains("ranchu")
      || Build.HARDWARE.contains("vbox86")
      || Build.HARDWARE.lowercase(Locale.ROOT).contains("nox")
      || Build.MANUFACTURER.contains("Genymotion")
      || Build.MODEL.contains("Android SDK built for x86")
      || Build.MODEL.contains("Emulator")
      || Build.MODEL.contains("google_sdk")
      || Build.MODEL.lowercase(Locale.ROOT).contains("droid4x")
      || Build.PRODUCT.contains("emulator")
      || Build.PRODUCT.contains("google_sdk")
      || Build.PRODUCT.contains("sdk_google")
      || Build.PRODUCT.contains("sdk_x86")
      || Build.PRODUCT.contains("sdk")
      || Build.PRODUCT.contains("simulator")
      || Build.PRODUCT.contains("vbox86p")
      || Build.PRODUCT.lowercase(Locale.ROOT).contains("nox")
    val isRooted = rootBeer.isRooted || isOnEmulator
    return isRooted
  }

  override fun checkBootloaderUnlocked(): Boolean {
    val bootloaderCheck = BootloaderCheck()
    return bootloaderCheck.isBootloaderUnlocked()
  }


  companion object {
    const val NAME = "RootChecker"
  }
}
