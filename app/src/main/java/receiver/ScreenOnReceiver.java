package receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import dao.JSONDataAccess;
import model.PhoneUsage;

public class ScreenOnReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        PhoneUsage newPhoneUsage = new PhoneUsage();
        newPhoneUsage.setStartTime(System.currentTimeMillis());

        JSONDataAccess.initPhoneUsage(context);
        Log.wtf("sumtag","ON!");
    }
}
