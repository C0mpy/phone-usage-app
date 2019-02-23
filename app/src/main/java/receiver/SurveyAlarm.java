package receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.MainActivity;

public class SurveyAlarm  {

    private static AlarmManager alarmManager;

    public static void start(Context context) {

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        boolean alarmUp = (PendingIntent.getBroadcast(context, 0,
                intent,
                PendingIntent.FLAG_NO_CREATE) != null);

        if (!alarmUp) {
            long currentTimeInMillis = System.currentTimeMillis();
            alarmManager = (AlarmManager)context.getSystemService(context.ALARM_SERVICE);
            alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    currentTimeInMillis + 3000000,
                    3000000,
                    pendingIntent);
            Log.wtf("myTag", "Alarm is NOT active");
        } else {
            Log.wtf("myTag", "Alarm is already active");
        }
    }
}
