//package receiver;
//
//import android.app.AlarmManager;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.util.Log;
//import com.example.MainActivity;
//import dao.database.metadata.MetadataDbHelper;
//import model.Metadata;
//import phone_usage_app.sw63.phoneusageapp.R;
//import util.Util;
//
//import java.io.IOException;
//import java.time.LocalTime;
//
//public class SurveyAlarm {
//
//    private static AlarmManager alarmManager;
//
//    public static void start(Context context) throws IOException {
//        Intent intent = new Intent(context, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
//
//        boolean alarmUp = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_NO_CREATE) != null;
//
//        if (!alarmUp) {
//            int fetchSurveyTimeInMinutes = context.getResources().getInteger(R.integer.fetchSurveyTimeInMinutes);
//
//            alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
//            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + Util.minutesToMillis(fetchSurveyTimeInMinutes),
//                                      Util.minutesToMillis(fetchSurveyTimeInMinutes), pendingIntent);
//        } else {
//            Log.d("AlarmManager", "Alarm is already active!");
//        }
//    }
//
//}
