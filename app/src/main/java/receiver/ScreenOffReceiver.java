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

                    db.beginTransaction();
                    PhoneUsage phoneUsage = JSONDataAccess.readCurrentPhoneUsageData(context);
                    ContentValues values = new ContentValues();
                    values.put("start_time", phoneUsage.getStartTime());
                    values.put("end_time", phoneUsage.getEndTime());
                    db.insert(PhoneUsageContract.PhoneUsageEntry.TABLE_NAME, null, values);
                    db.setTransactionSuccessful();
                    db.endTransaction();

                    db.beginTransaction();
                    String[] projection = {
                            BaseColumns._ID,
                            PhoneUsageContract.PhoneUsageEntry.COLUMN_NAME_START_TIME,
                            PhoneUsageContract.PhoneUsageEntry.COLUMN_NAME_END_TIME
                    };

                    Cursor cursor = db.query(
                            PhoneUsageContract.PhoneUsageEntry.TABLE_NAME,   // The table to query
                            null,             // The array of columns to return (pass null to get all)
                            null,              // The columns for the WHERE clause
                            null,          // The values for the WHERE clause
                            null,                   // don't group the rows
                            null,                   // don't filter by row groups
                            null               // The sort order
                    );


                    while(cursor.moveToNext()) {
                        String startTime = cursor.getString(
                                cursor.getColumnIndexOrThrow(PhoneUsageContract.PhoneUsageEntry.COLUMN_NAME_START_TIME));
                        String endTime = cursor.getString(
                                cursor.getColumnIndexOrThrow(PhoneUsageContract.PhoneUsageEntry.COLUMN_NAME_END_TIME));
                        Log.wtf("sumtag",startTime + " - " + endTime);
                    }
                    cursor.close();
                    db.setTransactionSuccessful();
                    db.endTransaction();
                }
            }).start();
        }

        Log.wtf("sumtag","OFF!");
    }
}
