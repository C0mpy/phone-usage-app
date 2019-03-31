package dao.database.phone_usage;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import dao.database.DatabaseHelper;
import mapper.PhoneUsageMapper;
import model.PhoneUsage;

public class PhoneUsageDbHelper {

    private static SQLiteDatabase db;
    private static DatabaseHelper databaseHelper;
    private static PhoneUsageDbHelper sInstance;

    private PhoneUsageDbHelper(Context context) {
        databaseHelper = DatabaseHelper.getInstance(context);
        db = databaseHelper.getWritableDatabase();
        db.enableWriteAheadLogging();
    }

    public static synchronized PhoneUsageDbHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PhoneUsageDbHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    public void save(PhoneUsage phoneUsage) {
        db.insert(PhoneUsageContract.PhoneUsageEntry.TABLE_NAME, null, PhoneUsageMapper.toContentValues(phoneUsage));
    }

    public void removeAll() {
        Cursor cursor = db.rawQuery(PhoneUsageContract.SQL_DELETE_ENTRIES, null);
        cursor.moveToNext();
        cursor.close();
    }

    public String sumPhoneUsage() {
        String sum = "";
        Cursor cursor = db.rawQuery(PhoneUsageContract.SQL_SUM_PHONE_USAGE, null);
        if (cursor.moveToFirst()) {
            sum = cursor.getString(0);
        }
        cursor.close();
        return sum;
    }

}
