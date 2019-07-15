package dao;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import model.Interval;
import model.Survey;

public class JSONDataAccess {

    private static Gson gson = new Gson();

    public static void initPhoneUsage(Context context) {
        Interval interval = new Interval();
        interval.setStartTime(System.currentTimeMillis());
        writeCurrentPhoneUsageData(interval, context);
    }

    public static void writeCurrentPhoneUsageData(Interval intervalData, Context context) {
        writeJsonObject("phoneUsage", intervalData, context);
    }

    public static Interval readCurrentPhoneUsageData(Context context) {
        return gson.fromJson(getJsonString("phoneUsage", context), Interval.class);
    }

    public static void writeActiveSurvey(Survey survey, Context context) {
        writeJsonObject("activeSurvey", survey, context);
    }

    public static Survey readActiveSurvey(Context context) {
        return gson.fromJson(getJsonString("activeSurvey", context), Survey.class);
    }

    private static String getJsonString(String dataName, Context context) {
        SharedPreferences prefs = context.getSharedPreferences(dataName, MODE_PRIVATE);
        return prefs.getString(dataName, null);
    }

    private static void writeJsonObject(String dataName, Object data, Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(dataName, MODE_PRIVATE).edit();
        editor.putString(dataName, gson.toJson(data));
        editor.commit();
    }
}
