package com.leandroperez.taf.core.session;

import com.leandroperez.taf.core.enumerator.PlatformInTest;

/**
 * @author Leandro Henrique Perez
 */

class SessionTimeStorage {
    PlatformInTest platformInTest = null;
    public long timeToSleepStep01;
    public long timeToSleepStep02;
    public long timeToSleepStep03;
    public long timeToSleepStep04;

    //default values for each platform
    public SessionTimeStorage(PlatformInTest platformInTest) {
        setDefaultValuesForSessionInPlatform(platformInTest);
    }

    public SessionTimeStorage() {
        setGenericDefaultValuesForSession();
    }

    public void setGenericDefaultValuesForSession() {
        timeToSleepStep01 = 5000;
        timeToSleepStep02 = 1000;
        timeToSleepStep03 = 6000;
        timeToSleepStep04 = 3000;
    }

    public void setDefaultValuesForSessionInPlatform(PlatformInTest platformInTest) {
        if (platformInTest == null) {
            throw new RuntimeException("Session configuration error, platformInTest is null");
        }
        this.platformInTest = platformInTest;

        switch (platformInTest) {
            case WEB:
                System.out.println("TimeStorageForSession for WEB session not implemented yet");
                break;
            case ANDROID:
                setValuesForSessionInPlatform(platformInTest, 4000, 1000, 6000, 3000);
                break;
            case IOS:
                setValuesForSessionInPlatform(platformInTest, 4000, 1000, 6000, 3000);
                break;
        }
    }

    public void setValuesForSessionInPlatform(PlatformInTest platformInTest, long step01, long step02, long step03, long step04) {
        if (platformInTest == null) {
            throw new RuntimeException("Session configuration error, platformInTest is null");
        }
        this.platformInTest = platformInTest;

        this.timeToSleepStep01 = step01;
        this.timeToSleepStep02 = step02;
        this.timeToSleepStep03 = step03;
        this.timeToSleepStep04 = step04;
    }
}
