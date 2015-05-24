Mobile Network Operator Tracker for Android
==============

This app keeps you updated with all your MNO service changes that can be silently painful (price change, new subscription to a paid service, etc.)

This software is designed to work with Russian MNOs: MTS, Beeline, Megafon.

Getting started
----
1. Install Android SDK (android-sdk-update-manager) using your distro
1. Run `tools/android` executable (probably needs root)
  * from "Tools" section check "Android SDK Tools", "Android Platform-tools", "Android SDK Build-tools"
  * from the section "Android 5.1.1 (API 21)" check "SDK Platform"
  * uncheck everything else or you'll waste your drive with unneeded stuff
  * Install - Accept License - Install
  * add paths to `tools` and `platform-tools` directories to your PATH environment variable
1. Connect your Android device with USB-cable and run `adb devices`
  * observe your device in the list and accept "Allow USB debugging" that appears on your device
  * if you don't see your device in the list—you need to enable Developer Options on your device (just Google how to do that for your device)
1. Install sbt, scala and icedtea (JDK) using your distro
1. Run `scala` to make sure that you're running the correct version of JVM (it should be OpenJDK); exit it
1. Run `./configure.sh` (the first run can take about 20 minutes to download all the dependencies to ~/.ivy2 directory)
1. From `sbt` command prompt you can
  * `eclipse` to generate eclipse project
  * `test`
  * `run` to compile and run on the device

License
----
This software is licensed under the terms of BSD-2 (read COPYING.txt for details).
