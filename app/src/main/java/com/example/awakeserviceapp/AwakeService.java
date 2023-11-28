package com.example.awakeserviceapp;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;

public class AwakeService extends Service {

    private final IBinder myBinder = new MyBinder();

    /**
     * MyBinder: custom implementation of a Binder as an inner class, used to get
     * the instance of the outer service class
     * */
    public class MyBinder extends Binder{
        AwakeService getService(){
            return AwakeService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public long getUptime(){
        return (SystemClock.elapsedRealtime())/1000; //elapsedRealtime gets number of millis since boot
    }
}