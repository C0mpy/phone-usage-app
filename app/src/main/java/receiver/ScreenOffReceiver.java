package receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import dao.JSONDataAccess;
import dao.database.DatabaseHelper;
import dao.database.interval.IntervalDbHelper;
import model.Interval;

public class ScreenOffReceiver extends BroadcastReceiver {

    private DatabaseHelper databaseHelper;
    private IntervalDbHelper intervalDbHelper;

    public ScreenOffReceiver(Context context) {
        databaseHelper = DatabaseHelper.getInstance(context);
        intervalDbHelper = IntervalDbHelper.getInstance(context);
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        Interval intervalData = JSONDataAccess.readCurrentPhoneUsageData(context);
        if(intervalData == null || intervalData.getStartTime() == 0) {
            intervalData = new Interval();
            intervalData.setStartTime(System.currentTimeMillis());
        }
        intervalData.setEndTime(System.currentTimeMillis());
        JSONDataAccess.writeCurrentPhoneUsageData(intervalData, context);

        new Thread(new Runnable() {
            public void run() {
                Interval interval = JSONDataAccess.readCurrentPhoneUsageData(context);

                databaseHelper.beginTransaction();
                intervalDbHelper.save(interval);
                databaseHelper.endTransaction();
            }
        }).start();
        Log.d("ScreenOffReceiver", "Screen is OFF!");
    }

}
