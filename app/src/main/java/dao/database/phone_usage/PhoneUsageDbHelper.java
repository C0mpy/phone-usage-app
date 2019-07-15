package dao.database.phone_usage;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import dao.database.DatabaseHelper;
import mapper.PhoneUsageMapper;
import model.Interval;

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

    public void save(Interval interval) {
        db.insert(PhoneUsageContract.PhoneUsageEntry.TABLE_NAME, null, PhoneUsageMapper.toContentValues(interval));
    }

    public void removeAll() {
        Cursor cursor = db.rawQuery(PhoneUsageContract.SQL_DELETE_ENTRIES, null);
        cursor.moveToNext();
        cursor.close();
    }

    public List<Interval> getPhoneUsage() {
        List<Interval> result = new ArrayList<>();
        Cursor cursor = db.rawQuery(PhoneUsageContract.SQL_GET_PHONE_USAGE, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                result.add(PhoneUsageMapper.mapToModel(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return result;
    }

}
