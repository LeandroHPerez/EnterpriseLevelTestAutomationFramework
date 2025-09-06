Instructions for SUT`s


******** For Android ********
Download Firefox app for Android for testing and install it on an emulator/simulator/device
https://ftp.mozilla.org/pub/fenix/releases/142.0/android/
Recomended version: 142.0

For convenience you can find an version for arm64-v8a  inside folder taf/app-sut-sample

Identify the app package name:
adb shell pm list packages -3

and for Acitivity name:
Open app in Emulator and run:
adb shell dumpsys window displays | grep -E mCurrentFocus

You will get something like org.mozilla.firefox/org.mozilla.firefox.App, where the first part before the slash (/) is the package name and the second part is the name of the main activity

Now, provide this information in taf/src/test/java/com/leandroperez/taf/config/uitest.properties



******** For iOS ********
Download Firefox app for iOS for testing:
https://github.com/mozilla-mobile/firefox-ios?tab=readme-ov-file

You need build iOS from source and install it to iOS simulator.

Brief instructions:
Clone repo: git clone https://github.com/mozilla-mobile/firefox-ios
The actual commit used from build Firefox app is: 0f995b7a7d4e160776a3d1e2ee287e7617206ee1
you can clone repo, after run (on main branch):
    git reset --hard 0f995b7a7d4e160776a3d1e2ee287e7617206ee1
    <OR>
    git branch specific-commit-branch 0f995b7a7d4e160776a3d1e2ee287e7617206ee1


Now, build app guided by: https://github.com/mozilla-mobile/firefox-ios?tab=readme-ov-file, for this SUT make sure to select the Firefox scheme in Xcode.