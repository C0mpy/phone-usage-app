package service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

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
        screenOnReceiver = new ScreenOnReceiver();
        IntentFilter filterOn = new IntentFilter(Intent.ACTION_SCREEN_ON);
        registerReceiver(screenOnReceiver, filterOn);

        screenOffReceiver = new ScreenOffReceiver();
        IntentFilter filterOff = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        registerReceiver(screenOffReceiver, filterOff);


        networkStateReceiver = new NetworkStateReceiver();
        IntentFilter filterNetworkOn = new IntentFilter();
        filterNetworkOn.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(networkStateReceiver, filterNetworkOn);
    }



//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        registerReceiver( new NetworkStateReceiver(), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
//        registerReceiver(new ScreenOffReceiver(), new IntentFilter(Intent.ACTION_SCREEN_OFF));
//        registerReceiver(new ScreenOnReceiver(), new IntentFilter(Intent.ACTION_SCREEN_ON));
//        return Service.START_STICKY;
//    }


}
