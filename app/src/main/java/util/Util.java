package util;

import android.content.Context;
import android.util.TypedValue;

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

}
