package receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.example.MainActivity;
import dao.database.metadata.MetadataDbHelper;
import model.Metadata;
import util.Util;

public class SurveyAlarm  {

    private static AlarmManager alarmManager;

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        boolean alarmUp = (PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_NO_CREATE) != null);

        if (!alarmUp) {
            MetadataDbHelper metadataDbHelper = MetadataDbHelper.getInstance(context);
            Metadata metadata = metadataDbHelper.findOne();

            alarmManager = (AlarmManager)context.getSystemService(context.ALARM_SERVICE);
            alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    metadata.getLastSurveyTakenTime() + Util.hoursToMillis(metadata.getTimeToNextSurveyInHours()),
                    Util.hoursToMillis(metadata.getTimeToNextSurveyInHours()),
                    pendingIntent);
        } else {
            Log.d("AlarmManager", "Alarm is already active!");
        }
    }


}
