package receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.example.MainActivity;
import dao.database.metadata.MetadataDbHelper;

public class SurveyAlarm  {

    private static AlarmManager alarmManager;

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        boolean alarmUp = (PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_NO_CREATE) != null);

        if (!alarmUp) {
            long currentTimeInMillis = System.currentTimeMillis();

            MetadataDbHelper metadataDbHelper = MetadataDbHelper.getInstance(context);
            Integer hoursToNextSurvey = metadataDbHelper.findOne().getTimeToNextSurveyInHours();

            alarmManager = (AlarmManager)context.getSystemService(context.ALARM_SERVICE);
            alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    currentTimeInMillis + 120000,
                    120000,
                    pendingIntent);
        } else {
            Log.d("AlarmManager", "Alarm is already active!");
        }
    }
}
