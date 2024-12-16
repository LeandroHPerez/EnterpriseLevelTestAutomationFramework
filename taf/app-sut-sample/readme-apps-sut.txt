Instructions for SUT`s


******** For Android ********
Download Firefox app for iOS for testing:
https://github.com/mozilla-mobile/firefox-android/releases/tag/klar-v125.3.0
Recomended version: klar-125.3.0-arm64-v8a.apk



******** For iOS ********
Download Firefox app for iOS for testing:
https://github.com/mozilla-mobile/firefox-ios?tab=readme-ov-file

You need build iOS from source and install it to iOS simulator.

Brief instructions:
Clone repo: git clone https://github.com/mozilla-mobile/firefox-ios
The actual commit used from build Firefox app is: 360b1debd
you can clone repo, after run:
    git reset --hard 360b1debd
    <OR>
    git branch specific-commit-branch 360b1debd


Now, build app guided by: https://github.com/mozilla-mobile/firefox-ios?tab=readme-ov-file