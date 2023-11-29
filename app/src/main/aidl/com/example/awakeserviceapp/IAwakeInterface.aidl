// IAwakeInterface.aidl
package com.example.awakeserviceapp;

// Declare any non-default types here with import statements

interface IAwakeInterface {

    /**
    * Call the AwakeService's getUptime function
    */
    long getUptime();

    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
}