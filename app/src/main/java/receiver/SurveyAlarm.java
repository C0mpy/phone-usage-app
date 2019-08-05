package receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.example.MainActivity;
import dao.JSONDataAccess;
import model.Survey;

public class SurveyAlarm {

    private static AlarmManager alarmManager;

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        boolean alarmUp = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_NO_CREATE) != null;

        if (!alarmUp) {
            Survey activeSurvey = JSONDataAccess.readActiveSurvey(context);

            alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
            // add a minute to end time, just to have some buffer space
            alarmManager.set(AlarmManager.RTC_WAKEUP, activeSurvey.getEndTime() + 1000 * 60 * 1, pendingIntent);
        } else {
            Log.d("AlarmManager", "Alarm is already active!");
        }
    }

}
