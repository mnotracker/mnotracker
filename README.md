Mobile Network Operator Tracker for Android
==============

This app keeps you updated with all your MNO service changes that can be silently painful (price change, new subscription to a paid service, etc.)

This software is designed to work with Russian MNOs: MTS, Beeline, Megafon and Tele2.

Getting started
----
### Android SDK
1. Install Android SDK (android-sdk-update-manager) using your distro
1. Make sure `${ANDROID_HOME}` points to Android SDK directory
1. Run `${ANDROID_HOME}/tools/android` executable (probably needs root)
  * from "Tools" section check "Android SDK Tools", "Android Platform-tools", "Android SDK Build-tools"
  * from "Android 5.1.1 (API 22)" check "SDK Platform"
  * from "Extras" check "Android Support Repository", "Android Support Library"
  * uncheck everything else or you'll waste your drive with unneeded stuff
  * Install - Accept License - Install
  * add paths to `${ANDROID_HOME}/tools` and `${ANDROID_HOME}/platform-tools` directories to your `${PATH}` environment variable
1. Connect your Android device with USB-cable and run `adb devices`
  * observe your device in the list and accept "Allow USB debugging" that appears on your device
  * if you don't see your device in the list—you need to enable Developer Options on your device (just Google how to do that for your device)
  * if you still don't see your device in the list—you need to set up udev rule to be able to detect your device correctly (see "Set up your system to detect your device" [here](https://developer.android.com/tools/device.html#setting-up))

### Scala, sbt and IcedTea JDK
1. Install Scala, sbt and IcedTea JDK using your distro
1. Run `scala` to make sure that you're running the correct version of JVM (it should be OpenJDK); exit it
1. Run `./configure.sh` (the first run can take about 20 minutes to download all the dependencies to ~/.ivy2 directory)
1. From `sbt` command prompt you can
  * `test`
  * `run` to compile and run on the device
  * `~ ; test ; scalastyle` to run continuous integration testing

### Debug tools
You can use `${ANDROID_HOME}/tools/monitor` or [QDeviceMonitor](https://github.com/alopatindev/qdevicemonitor#qdevicemonitor) as a log viewer

License
----
This software is licensed under the terms of BSD-2 (read COPYING.txt for details).
