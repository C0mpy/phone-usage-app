package service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import receiver.NetworkStateReceiver;
import receiver.ScreenOffReceiver;
import receiver.ScreenOnReceiver;

public class StartReceiversService extends Service {

    private static ScreenOnReceiver screenOnReceiver;
    private static ScreenOffReceiver screenOffReceiver;
    private static NetworkStateReceiver networkStateReceiver;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        registerReceivers();
    }

    public int onStartCommand() {
        registerReceivers();
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(screenOnReceiver);
        unregisterReceiver(screenOffReceiver);
        unregisterReceiver(networkStateReceiver);
        screenOnReceiver = null;
        screenOffReceiver = null;
        networkStateReceiver = null;
    }

    private void registerReceivers() {
        registerScreenOnReceiver();
        registerScreenOffReceiver();
        registerNetworkStateReceiver();
    }

    private void registerScreenOnReceiver() {
        try {
            unregisterReceiver(screenOnReceiver);
        } catch (Exception e) {
            Log.d("StartReceiversService", "screenOnReceiver is already active");
        }
        screenOnReceiver = new ScreenOnReceiver();
        IntentFilter filterOn = new IntentFilter(Intent.ACTION_SCREEN_ON);
        registerReceiver(screenOnReceiver, filterOn);
    }

    private void registerScreenOffReceiver() {
        try {
            unregisterReceiver(screenOffReceiver);
        } catch (Exception e) {
            Log.d("StartReceiversService", "screenOffReceiver is already active");
        }
        screenOffReceiver = new ScreenOffReceiver(this);
        IntentFilter filterOff = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        registerReceiver(screenOffReceiver, filterOff);
    }

    private void registerNetworkStateReceiver() {
        try {
            unregisterReceiver(networkStateReceiver);
        } catch (Exception e) {
            Log.d("StartReceiversService", "networkStateReceiver is already active");
        }
        networkStateReceiver = new NetworkStateReceiver(this);
        IntentFilter filterNetworkOn = new IntentFilter();
        filterNetworkOn.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(networkStateReceiver, filterNetworkOn);
    }

}
