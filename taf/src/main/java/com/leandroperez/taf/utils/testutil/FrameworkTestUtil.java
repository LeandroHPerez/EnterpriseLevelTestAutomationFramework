package com.leandroperez.taf.utils.testutil;

import com.leandroperez.taf.core.enumerator.PlatformInTest;

/**
 * @author Leandro Henrique Perez
 */

public class FrameworkTestUtil {

    public boolean isPlatformInTestNull(PlatformInTest platformInTest) {
        if (platformInTest == null) {
            System.out.println(this.getClass().getEnclosingMethod().getDeclaringClass().getEnclosingMethod().getName()
                    + "\n" + "Error: " + "platformInTest must be NOT null");
            return true;
        }
        return false;
    }

    public Boolean isPlatformInTestMobile(PlatformInTest platformInTest) {
        if (isPlatformInTestNull(platformInTest)) return null;

        if (platformInTest != PlatformInTest.ANDROID && platformInTest != PlatformInTest.IOS) {
            System.out.println(this.getClass().getEnclosingMethod().getDeclaringClass().getEnclosingMethod().getName()
                    + "\n" + "Error: " + "IsPlatformInMobile error: platformInTest is not iOS or Android");
            return false;
        }
        return true;
    }

    public Boolean isPlatformInTestWeb(PlatformInTest platformInTest) {
        if (isPlatformInTestNull(platformInTest)) return null;

        if (platformInTest != PlatformInTest.WEB) {
            System.out.println(this.getClass().getEnclosingMethod().getDeclaringClass().getEnclosingMethod().getName()
                    + "\n" + "Error: " + "IsPlatformInMobile error: platformInTest is not WEB");
            return false;
        }
        return true;
    }
}
