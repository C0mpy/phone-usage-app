package receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.MainActivity;

public class SurveyAlarm  {

    private static AlarmManager alarmManager;

    public static void start(Context context) {
        if(alarmManager == null) {
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            long currentTimeInMillis = System.currentTimeMillis();
            AlarmManager alarmManager = (AlarmManager)context.getSystemService(context.ALARM_SERVICE);
            alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    currentTimeInMillis + 2000000,
                    2000000,
                    pendingIntent);
        }
    }
}
