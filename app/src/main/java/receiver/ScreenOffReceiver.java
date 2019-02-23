package receiver;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import dao.JSONDataAccess;
import dao.database.PhoneUsageContract;
import dao.database.PhoneUsageDbHelper;
import model.PhoneUsage;

public class ScreenOffReceiver extends BroadcastReceiver {

    Context context;

    @Override
    public void onReceive(final Context context, Intent intent) {
        this.context = context;
        final PhoneUsage phoneUsageData = JSONDataAccess.readCurrentPhoneUsageData(context);
        if(phoneUsageData.getStartTime() != 0L) {
            phoneUsageData.setEndTime(System.currentTimeMillis());
            JSONDataAccess.writeCurrentPhoneUsageData(phoneUsageData, context);

            // save phone usage data to database asynchronouslly
            new Thread(new Runnable() {
                public void run() {
                    PhoneUsageDbHelper phoneUsageDbHelper = new PhoneUsageDbHelper(context);
                    SQLiteDatabase db = phoneUsageDbHelper.getWritableDatabase();

                    db.beginTransaction();
                    PhoneUsage phoneUsage = JSONDataAccess.readCurrentPhoneUsageData(context);
                    ContentValues values = new ContentValues();
                    values.put("start_time", phoneUsage.getStartTime());
                    values.put("end_time", phoneUsage.getEndTime());
                    db.insert(PhoneUsageContract.PhoneUsageEntry.TABLE_NAME, null, values);
                    db.setTransactionSuccessful();
                    db.endTransaction();
                }
            }).start();
        }

        Log.d("ScreenOffReceiver","Screen is OFF!");
    }
}
