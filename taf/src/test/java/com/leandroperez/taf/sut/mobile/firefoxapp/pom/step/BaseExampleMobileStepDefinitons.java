package com.leandroperez.taf.sut.mobile.firefoxapp.pom.step;

import com.leandroperez.taf.core.Session;
import com.leandroperez.taf.core.enumerator.PlatformInTest;
import com.leandroperez.taf.core.enumerator.TestExecutionStrategy;
import io.cucumber.java.After;
import io.cucumber.java.Before;


public class BaseExampleMobileStepDefinitons {

    Session session = new Session();

    public void initDefaultSession() {
        System.out.println("initDefaultSession()");
        try{
            System.out.println("initiating Session");
            session.startSession(PlatformInTest.ANDROID, TestExecutionStrategy.LOCAL);

        } catch (Exception e) {
            System.out.println("initSession(): " + "Error: " + e);

        }
    }

    public void initSession(PlatformInTest platformInTest) {
        System.out.println("initSession() in platform: " + platformInTest);
        try{
            System.out.println("initiating Session");
            session.startSession(platformInTest, TestExecutionStrategy.LOCAL);

        } catch (Exception e) {
            System.out.println("initSession(): " + "Error: " + e);

        }
    }

    @After
    public void closeSession() {
        System.out.println("closeSession()");
        try{
            if(session != null) {
                System.out.println("closing Session");
                session.closeSession();
            }
        } catch (Exception e){
            System.out.println("closeSession(): " + "Error: " + e);
        }
    }
}
