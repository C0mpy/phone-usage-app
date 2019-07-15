package receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import dao.JSONDataAccess;

public class ScreenOnReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        JSONDataAccess.initPhoneUsage(context);
        Log.d("ScreenOnReceiver","Screen is ON!");
    }
}
