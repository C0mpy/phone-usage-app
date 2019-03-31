package receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import dao.JSONDataAccess;
import dao.database.DatabaseHelper;
import dao.database.phone_usage.PhoneUsageDbHelper;
import model.PhoneUsage;

public class ScreenOffReceiver extends BroadcastReceiver {

    private DatabaseHelper databaseHelper;
    private PhoneUsageDbHelper phoneUsageDbHelper;

    public ScreenOffReceiver(Context context) {
        databaseHelper = DatabaseHelper.getInstance(context);
        phoneUsageDbHelper = PhoneUsageDbHelper.getInstance(context);
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        PhoneUsage phoneUsageData = JSONDataAccess.readCurrentPhoneUsageData(context);
        if(phoneUsageData == null || phoneUsageData.getStartTime() == 0) {
            phoneUsageData = new PhoneUsage();
            phoneUsageData.setStartTime(System.currentTimeMillis());
        }
        phoneUsageData.setEndTime(System.currentTimeMillis());
        JSONDataAccess.writeCurrentPhoneUsageData(phoneUsageData, context);

        new Thread(new Runnable() {
            public void run() {
                PhoneUsage phoneUsage = JSONDataAccess.readCurrentPhoneUsageData(context);

                databaseHelper.beginTransaction();
                phoneUsageDbHelper.save(phoneUsage);
                databaseHelper.endTransaction();
            }
        }).start();
        Log.d("ScreenOffReceiver", "Screen is OFF!");
    }

}
