package com.explodingpixels.util;

public class PlatformUtils {

    private PlatformUtils() {
        // utility class - no constructor needed.
    }

    /**
     * Get's the version of Java currently running.
     *
     * @return the version of Java that is running.
     */
    public static String getJavaVersion() {
        return System.getProperty("java.version");
    }

    /**
     * True if this JVM is running on a Mac.
     *
     * @return true if this JVM is running on a Mac.
     */
    public static boolean isMac() {
        System.out.println(System.getProperty("os.name"));
        return System.getProperty("os.name").startsWith("Mac OS");
    }

    /**
     * True if this JVM is running on a Mac and is Java for Mac OS X Update 2 (1.5.0_16).
     *
     * @return true if this JVM is running on a Mac and is Java for Mac OS X Update 2 (1.5.0_16).
     */
    public static boolean isMacJavaUpdate2() {
        return isMac() && getJavaVersion().equals("1.5.0_16");
    }

}
