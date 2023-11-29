package com.example.awakeserviceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private final long awaitTimeInMillis = 1000;
    private boolean isBound = false;
    private TextView awakeTextView;

    private IAwakeInterface awakeInterface;
    private Intent serviceIntent;

    private Handler handler;
    private Runnable timerRunnable;

    private final ServiceConnection con = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            awakeInterface = IAwakeInterface.Stub.asInterface(service);
            isBound = true;
            handler.post(timerRunnable);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        awakeTextView = (TextView) findViewById(R.id.awakeData);

        handler = new Handler(Looper.getMainLooper());
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                updateAwakeTextView();
                handler.postDelayed(this,awaitTimeInMillis);
            }
        };

        serviceIntent = new Intent(this, AwakeService.class);
        startService(serviceIntent);
        bindService(serviceIntent, con, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isBound){
            unbindService(con);
            isBound = false;
        }
        stopService(serviceIntent);
    }

    private void updateAwakeTextView(){
        if (isBound){
            try{
                long awakeSeconds = awakeInterface.getUptime();
                long awakeHours = awakeSeconds / 3600;
                awakeSeconds %= 3600;
                long awakeMinutes = awakeSeconds / 60;
                awakeSeconds %= 60;

                awakeTextView.setText(String.format(Locale.getDefault(),"Awake time: %d hours, %d minutes and %d seconds", awakeHours, awakeMinutes, awakeSeconds));
            }
            catch(RemoteException ex){
                ex.printStackTrace();
            }
        }
    }
}