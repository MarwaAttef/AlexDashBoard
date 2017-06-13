package com.example.wedad.design.welcomscreens.singletonepkg;

/**
 * Created by wedad on 5/24/2017.
 */

public class CoordinatesSingleTone {
    public static double lat;
    public static double log;
    private static CoordinatesSingleTone instance;

    public static void initInstance() {
        if (instance == null) {
            // Create the instance
            instance = new CoordinatesSingleTone();
        }
    }

    public static CoordinatesSingleTone getInstance() {
        // Return the instance
        return instance;
    }
}

