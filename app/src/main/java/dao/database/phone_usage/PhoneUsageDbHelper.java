package dao.database.phone_usage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import dao.database.metadata.MetadataContract;
import dao.database.DatabaseHelper;
import model.PhoneUsage;

public class PhoneUsageDbHelper {

    private static SQLiteDatabase db;
    private static Context context;
    private static DatabaseHelper databaseHelper;
    private static PhoneUsageDbHelper sInstance;

    private PhoneUsageDbHelper(Context _context) {
        context = _context;
        databaseHelper = DatabaseHelper.getInstance(context);
        db = databaseHelper.getWritableDatabase();
    }

    public static synchronized PhoneUsageDbHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PhoneUsageDbHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    public void save(PhoneUsage phoneUsage) {
        db.beginTransaction();

        ContentValues values = new ContentValues();
        values.put(PhoneUsageContract.PhoneUsageEntry.COLUMN_NAME_START_TIME, phoneUsage.getStartTime());
        values.put(PhoneUsageContract.PhoneUsageEntry.COLUMN_NAME_END_TIME, phoneUsage.getEndTime());

        db.insert(PhoneUsageContract.PhoneUsageEntry.TABLE_NAME, null, values);
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public Integer count() {
        Integer result = null;
        db.beginTransaction();
        Cursor cursor = db.rawQuery(MetadataContract.COUNT_ENTRIES, null);
        if (cursor.moveToFirst()) {
            String str = cursor.getString(0);
            result = Integer.valueOf(str);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        return result;
    }

    public void removeAll() {
        db.beginTransaction();

        Cursor cursor = db.rawQuery(PhoneUsageContract.SQL_DELETE_ENTRIES, null);
        cursor.close();

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public String sumPhoneUsage() {
        String sum = "";

        db.beginTransaction();
        Cursor cursor = db.rawQuery(PhoneUsageContract.SQL_SUM_PHONE_USAGE, null);
        if (cursor.moveToFirst()) {
            sum = cursor.getString(0);
        }
        cursor.close();
        db.setTransactionSuccessful();
        db.endTransaction();

        return sum;
    }

}
