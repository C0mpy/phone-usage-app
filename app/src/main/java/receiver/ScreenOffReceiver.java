package receiver;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import dao.JSONDataAccess;
import dao.database.PhoneUsageContract;
import dao.database.PhoneUsageDbHelper;
import model.PhoneUsage;

public class ScreenOffReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        final PhoneUsage phoneUsageData = JSONDataAccess.readCurrentPhoneUsageData(context);
        if(phoneUsageData.getStartTime() != 0L) {
            phoneUsageData.setEndTime(System.currentTimeMillis());
            JSONDataAccess.writeCurrentPhoneUsageData(phoneUsageData, context);

            // put phone usage data to database asynchronouslly
            new Thread(new Runnable() {
                public void run() {
                    PhoneUsageDbHelper phoneUsageDbHelper = new PhoneUsageDbHelper(context);
                    SQLiteDatabase db = phoneUsageDbHelper.getWritableDatabase();

                    PhoneUsage phoneUsage = JSONDataAccess.readCurrentPhoneUsageData(context);
                    ContentValues values = new ContentValues();
                    values.put("startTime", phoneUsage.getStartTime());
                    values.put("endTime", phoneUsage.getEndTime());
                    db.insert(PhoneUsageContract.PhoneUsageEntry.TABLE_NAME, null, values);
                }
            }).start();
        }

        Log.wtf("sumtag","OFF!");
    }
}
