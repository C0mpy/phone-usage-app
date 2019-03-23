package receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import dao.JSONDataAccess;
import dao.database.DatabaseHelper;
import dao.database.phone_usage.PhoneUsageDbHelper;
import model.PhoneUsage;

public class ScreenOffReceiver extends BroadcastReceiver {

    private Context context;
    private DatabaseHelper databaseHelper;
    private PhoneUsageDbHelper phoneUsageDbHelper;

    public ScreenOffReceiver(Context context) {
        this.context = context;
        databaseHelper = DatabaseHelper.getInstance(context);
        phoneUsageDbHelper = PhoneUsageDbHelper.getInstance(context);
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        PhoneUsage phoneUsageData = JSONDataAccess.readCurrentPhoneUsageData(context);
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
    }

}
