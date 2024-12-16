package com.leandroperez.taf.utils.threadutil;

/**
 * @author Leandro Henrique Perez
 */

public class ThreadUtil {
    public static void sleepSafe(long millis) {
        if (millis <= 0) {
            return;
        }
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getStackTrace()[2].getMethodName() + "\n" + e + "\nError during wait/sleep. Waiting time: " + millis);
        }
    }
}
