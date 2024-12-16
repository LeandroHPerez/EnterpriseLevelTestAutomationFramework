package com.leandroperez.taf.sut.mobile.firefoxapp.pom.step;

import com.leandroperez.taf.core.enumerator.PlatformInTest;
import com.leandroperez.taf.core.enumerator.TestExecutionStrategy;
import com.leandroperez.taf.core.session.Session;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Leandro Henrique Perez
 */

public class BaseExampleMobileStepDefinitons {

    Session session = null;

    public void initDefaultSession() {
        System.out.println("initDefaultSession()");
        try {
            System.out.println("initiating Session");
            session.startSession(PlatformInTest.ANDROID, TestExecutionStrategy.LOCAL);

        } catch (Exception e) {
            System.out.println("initSession(): " + "Error: " + e);

        }
    }

    public void initSession(PlatformInTest platformInTest) {
        System.out.println("initSession() in platform: " + platformInTest);
        try {
            System.out.println("initiating Session");
            session = new Session();
            session.startSession(platformInTest, TestExecutionStrategy.LOCAL);

        } catch (Exception e) {
            System.out.println("initSession(): " + "Error: " + e);

        }
    }

    public void AssertInitSession(String os) {
        assertTrue(os != null && !os.isEmpty(), "Invalid value to OS");
        PlatformInTest platform = PlatformInTest.valueOf(os.toUpperCase());
        initSession(platform);
        assertNotNull(session, "Error in test: Session is null");
    }

    public void closeSession() {
        System.out.println("closeSession()");
        try {
            if (session != null) {
                System.out.println("closing Session");
                session.closeSession();
            }
        } catch (Exception e) {
            System.out.println("closeSession(): " + "Error: " + e);
        }
    }
}
