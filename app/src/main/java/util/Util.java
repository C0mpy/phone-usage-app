package util;

import android.content.Context;
import android.util.TypedValue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    public static float convertToDp(int pixels, Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pixels, context.getResources().getDisplayMetrics());
    }

    public static long daysToMilis(int days) {
        return days * hoursToMillis(24);
    }

    public static long hoursToMillis(int hours) {
        return hours * minutesToMillis(60);
    }

    public static long minutesToMillis(int minutes) {
        return minutes * 60 * 1000;
    }

    public static long millisToMinutes(long millis) {
        return millis / 60 / 1000;
    }

    public static String millisToDate(long millis) {
        DateFormat simple = new SimpleDateFormat("dd MMM yyyy HH:mm");
        Date date = new Date(millis);
        return simple.format(date);
    }

}
