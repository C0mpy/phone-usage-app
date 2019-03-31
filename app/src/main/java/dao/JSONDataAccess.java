package dao;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import model.PhoneUsage;

public class JSONDataAccess {

    private static Gson gson = new Gson();

    public static void initPhoneUsage(Context context) {
        PhoneUsage phoneUsage = new PhoneUsage();
        phoneUsage.setStartTime(System.currentTimeMillis());
        writeCurrentPhoneUsageData(phoneUsage, context);
    }

    public static void writeCurrentPhoneUsageData(PhoneUsage phoneUsageData, Context context) {
        writeJsonObject("phoneUsage", phoneUsageData, context);
    }

    public static PhoneUsage readCurrentPhoneUsageData(Context context) {
        return gson.fromJson(getJsonString("phoneUsage", context), PhoneUsage.class);
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
