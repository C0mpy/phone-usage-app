package receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import dao.JSONDataAccess;
import dao.database.phone_usage.PhoneUsageDbHelper;
import model.PhoneUsage;

public class ScreenOffReceiver extends BroadcastReceiver {

    Context context;

    @Override
    public void onReceive(final Context context, Intent intent) {
        this.context = context;
        final PhoneUsage phoneUsageData = JSONDataAccess.readCurrentPhoneUsageData(context);
        phoneUsageData.setEndTime(System.currentTimeMillis());
        JSONDataAccess.writeCurrentPhoneUsageData(phoneUsageData, context);

        // save phone usage data to database asynchronouslly
        new Thread(new Runnable() {
            public void run() {
                PhoneUsage phoneUsage = JSONDataAccess.readCurrentPhoneUsageData(context);
                PhoneUsageDbHelper phoneUsageDbHelper = PhoneUsageDbHelper.getInstance(context);
                phoneUsageDbHelper.save(phoneUsage);
            }
        }).start();

    }
}
