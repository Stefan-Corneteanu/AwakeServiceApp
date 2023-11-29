package com.example.awakeserviceapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;

public class AwakeService extends Service {

    /**
     * Custom implementation of the IAwakeInterface STUB that overrides the
     * getUptime function*/
    private final IAwakeInterface.Stub binder = new IAwakeInterface.Stub(){

        @Override
        public long getUptime() throws RemoteException {
            return AwakeService.this.getUptime();
        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
            //nothing
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public long getUptime(){
        return (SystemClock.elapsedRealtime())/1000; //elapsedRealtime gets number of millis since boot
    }
}